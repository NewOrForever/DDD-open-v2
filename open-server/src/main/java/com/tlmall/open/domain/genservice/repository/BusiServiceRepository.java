package com.tlmall.open.domain.genservice.repository;

import com.tlmall.open.domain.genservice.adaptor.GsService;

/**
 * @author ：楼兰
 * @description:
 **/


public interface BusiServiceRepository {

    GsService getGsService(String serviceCode);
}