package com.tlmall.open.domain.genservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.clientmanage.adaptor.ClientInfoInterface;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.CommonGsResponse;
import com.tlmall.open.domain.genservice.service.MessageCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author loulan
 * @desc
 */
@Service
public class MessageCheckServiceImpl implements MessageCheckService {
    @Resource
    private ClientInfoInterface clientInfoInterface;

    public CommonGsResponse checkSimpleMsg(SimpleRequestMsg requestMsg) {
        CommonGsResponse response = null;
        final SimpleRequestMsgHeader requestMsgHeader = requestMsg.getRequestMsgHeader();
        final JSONObject requestMsgBody = requestMsg.getRequestMsgBody();
        if (null == requestMsgHeader || null == requestMsgBody) {
            response = new CommonGsResponse("", "", "", CommonGsResponse.RESULT_CODE_FORMAT_ERROR, "接口数据错误");
        } else if (StrUtil.isEmpty(requestMsgHeader.getTransId()) ||
                StrUtil.isEmpty(requestMsgHeader.getServiceCode()) ||
                StrUtil.isEmpty(requestMsgHeader.getSysId())) {
            response = new CommonGsResponse("", "", "", CommonGsResponse.RESULT_CODE_PARAM_ERROR, "参数处理错误");
        } else if (!clientInfoInterface.isContainSys(requestMsgHeader.getSysId())) {
            response = new CommonGsResponse("", "", "", CommonGsResponse.RESULT_CODE_SYSID_ERROR, "无效的系统标识");
        } else {
            response = new CommonGsResponse(requestMsgHeader.getServiceCode(), requestMsgHeader.getTransId(),
                    requestMsgHeader.getSysId(), CommonGsResponse.RESULT_CODE_SUCCESS, "请求接收成功");
        }
        return response;
    }
}
