package com.tlmall.open.domain.genservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.clientmanage.adaptor.ClientInfoInterface;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.DecryptMessageException;
import com.tlmall.open.domain.genservice.entity.EncryptedRequestMsg;
import com.tlmall.open.domain.genservice.entity.EncryptedRequestMsgHeader;
import com.tlmall.open.domain.genservice.service.DecryptService;
import com.tlmall.open.utils.AESUtil;
import com.tlmall.open.utils.RsaUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author loulan
 * @desc
 */
@Service
public class DecryptServiceImpl implements DecryptService {

    @Resource
    ClientInfoInterface clientInfoInterface;

    //这个地方传的参数最好要封装成一个对象，Domian Primative。 这里偷个懒
    public SimpleRequestMsg decrypData(String ftRequestInfo) throws DecryptMessageException {
        final EncryptedRequestMsg encryptedRequestMsg = JSONObject.parseObject(ftRequestInfo, EncryptedRequestMsg.class);
        if(null == encryptedRequestMsg){
            throw new DecryptMessageException("消息格式异常；请配合客户端程序使用");
        }
        final EncryptedRequestMsgHeader reqHeader = encryptedRequestMsg.getHeader();
        final String encryptedBody = encryptedRequestMsg.getBody();
        if(null == reqHeader || null == encryptedBody){
            throw new DecryptMessageException("请求参数错误；请配合客户端程序使用");
        }
        String sysPrivateKey = clientInfoInterface.getPrivateKey(reqHeader.getSysId());
        if(StrUtil.isEmpty(sysPrivateKey)){
            throw new DecryptMessageException("无效的sysId；请检查客户端参数");
        }
        String aesKey;
        String requestBody;
        try{
            aesKey = RsaUtils.RSADecodeByPrivateKey(sysPrivateKey, reqHeader.getKey());
            requestBody = AESUtil.decryptByAES(encryptedBody, aesKey).replace("\"","").replace("\\","\"");
        }catch (Exception e){
            throw new DecryptMessageException("消息解密错误；请检查公钥是否正确");
        }

        SimpleRequestMsg requestMsg = new SimpleRequestMsg();
        //key字段解密后就丢弃掉
        SimpleRequestMsgHeader requestMsgHeader = new SimpleRequestMsgHeader();
        requestMsgHeader.setSysId(reqHeader.getSysId());
        requestMsgHeader.setServiceCode(reqHeader.getServiceCode());
        requestMsgHeader.setTransId(reqHeader.getTransId());
        requestMsgHeader.setSysUser(reqHeader.getSysUser());
        requestMsgHeader.setSysPwd(reqHeader.getSysPwd());
        requestMsgHeader.setInTime(reqHeader.getInTime());

        requestMsg.setRequestMsgHeader(requestMsgHeader);
        requestMsg.setRequestMsgBody(JSON.parseObject(requestBody));

        return requestMsg;
    }
}
