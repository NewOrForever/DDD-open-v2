package com.tlmall.open.domain.hisrequest.adaptor.impl;

import com.tlmall.open.domain.hisrequest.adaptor.HisRequestInterface;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import com.tlmall.open.domain.hisrequest.infrastructure.HisConfig;
import com.tlmall.open.domain.hisrequest.repository.HisRequestRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author loulan
 * @desc
 */
@Service
public class HisRequestInterfaceImpl implements HisRequestInterface {
    @Resource
    private HisRequestRepository hisRequestRepository;

    @Resource
    private HisConfig hisConfig;

    private final Logger logger = Logger.getLogger(HisRequestInterfaceImpl.class);

    @Override
    public boolean addHisRequest(HisRequest hisRequest) {
        return hisRequestRepository.addHisRequest(hisRequest) > 0;
    }

    @Override
    public void addTransLogAppender(Logger logger, String transId) {
        hisConfig.addTransLogAppender(logger,transId);
    }

    @Override
    public void removeTransLogAppender(Logger logger) {
        hisConfig.removeTransLogAppender(logger);
    }

    @Override
    public List<HisRequest> queryHisRequest(String transId, String serviceCode) {
        return hisRequestRepository.queryHisRequest(transId,serviceCode);
    }
}
