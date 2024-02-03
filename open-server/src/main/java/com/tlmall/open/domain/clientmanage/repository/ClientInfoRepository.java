package com.tlmall.open.domain.clientmanage.repository;

import com.tlmall.open.domain.clientmanage.entity.ClientInfo;

import java.util.List;

/**
 * @author loulan
 * @desc
 */
public interface ClientInfoRepository {
    int saveClient(ClientInfo client);

    int deleteSysById(String sysId);

    List<ClientInfo> queryAllSys();
}
