package com.tlmall.open.domain.clientmanage.repository;

import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import com.tlmall.open.domain.clientmanage.service.ClientInfoDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author loulan
 * @desc
 */
@Component
public class SysCache {
    private static Map<String, ClientInfo> gsmanageMap = new HashMap<>();

    @Autowired
    private ClientInfoDomainService clientInfoDomainService;
    @PostConstruct
    public void init() {
        final List<ClientInfo> clientInfos = clientInfoDomainService.queryAllSys();
        if(null != clientInfos && !clientInfos.isEmpty()){
            clientInfos.forEach(clientInfo -> {
                gsmanageMap.put(clientInfo.getSysId(),clientInfo);
            });
        }
    }

    public void reLoadConfig() {
        gsmanageMap.clear();
        this.init();
    }

    public ClientInfo getClient(String sysId){
        return gsmanageMap.containsKey(sysId)?gsmanageMap.get(sysId):null;
    }

    public boolean checkSysId(String sysId) {
        return gsmanageMap.containsKey(sysId);
    }
}
