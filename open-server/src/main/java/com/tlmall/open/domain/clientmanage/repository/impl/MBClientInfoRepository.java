package com.tlmall.open.domain.clientmanage.repository.impl;

import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import com.tlmall.open.domain.clientmanage.infrastructure.ClientInfoMapper;
import com.tlmall.open.domain.clientmanage.repository.ClientInfoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author loulan
 * @desc
 */
@Component
public class MBClientInfoRepository implements ClientInfoRepository{

    @Resource
    private ClientInfoMapper clientInfoMapper;

    @Override
    public int saveClient(ClientInfo client) {
        return clientInfoMapper.insert(client);
    }

    @Override
    public int deleteSysById(String sysId) {
        return clientInfoMapper.deleteById(sysId);
    }

    @Override
    public List<ClientInfo> queryAllSys() {
        return clientInfoMapper.selectList(null);
    }
}
