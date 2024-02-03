package com.tlmall.open.domain.clientmanage.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import com.tlmall.open.domain.clientmanage.repository.ClientInfoRepository;
import com.tlmall.open.domain.clientmanage.repository.SysCache;
import com.tlmall.open.domain.clientmanage.infrastructure.ThreadPoolHolder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author loulan
 * @desc
 */
@Service
public class ClientInfoDomainService {

    private final Logger logger = Logger.getLogger(ClientInfoDomainService.class);

    @Resource
    private ClientInfoRepository clientInfoRepository;
    @Resource
    private SysCache sysCache;
    public int saveSys(ClientInfo client) {
        return clientInfoRepository.saveClient(client);
    }

    public int deleteSysById(String sysId) {
        return clientInfoRepository.deleteSysById(sysId);
    }

    public List<ClientInfo> queryAllSys() {
        return clientInfoRepository.queryAllSys();
    }

    public Boolean isContainSys(String sysId){
        return sysCache.checkSysId(sysId);
    }

    public ClientInfo getSysManage(String sysId){
        return sysCache.getClient(sysId);
    }

    public void sendMessageAsync(String sysId, String message){
        logger.info("异步推送报文:目标系统=>"+sysId+";报文内容 =》 "+message);
        ThreadPoolHolder.pushMessageExecutor.execute(()->{
            doSend(sysId,message);
        });
    }
    //FIXME 这里业务处理简单一点，只管向业务系统推送，业务系统有没有接收到就先不管了。
    private void doSend(String sysId, String message){
        try{
            final ClientInfo clientInfo = sysCache.getClient(sysId);
            final String notifyparam = clientInfo.getNotifyParam();
            if(StrUtil.isBlank(notifyparam)){
                HttpUtil.post(clientInfo.getNotifyUrl(),message);
            }else{
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(notifyparam,message);
                HttpUtil.post(clientInfo.getNotifyUrl(),paramMap);
            }
        }catch (Exception e){
            logger.error(message + " ," + e.getMessage(), e);
        }
    }

    public void reLoadConfig() {
        sysCache.reLoadConfig();
    }
}
