package com.tlmall.open.domain.clientmanage.adaptor.impl;

import cn.hutool.http.HttpUtil;
import com.tlmall.open.domain.clientmanage.adaptor.ClientInfoInterface;
import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import com.tlmall.open.domain.clientmanage.service.ClientInfoDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author loulan
 * @desc
 */
@Service
public class ClientInfoInterfaceImpl implements ClientInfoInterface {
    @Resource
    private ClientInfoDomainService clientInfoDomainService;

    public String getPrivateKey(String sysId) {
        ClientInfo clientInfo = clientInfoDomainService.getSysManage(sysId);
        if(null == clientInfo){
            return "";
        }
        return  clientInfo.getPrivatekey();
    }

    public boolean sendHttpResponse(String sysId, String message) {
//        ClientInfo clientInfo = clientInfoDomainService.getSysManage(sysId);
//        if(null == clientInfo){
//            return false;
//        }
//        HttpUtil.post(clientInfo.getNotifyUrl(), message);
//        return true;
        clientInfoDomainService.sendMessageAsync(sysId, message);
        return true;
    }

    @Override
    public boolean isContainSys(String sysId) {
        return clientInfoDomainService.isContainSys(sysId);
    }
}
