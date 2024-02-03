package com.tlmall.open.domain.hisrequest.service;

import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import com.tlmall.open.domain.hisrequest.entity.HisRequestLog;
import com.tlmall.open.domain.hisrequest.infrastructure.HisConfig;
import com.tlmall.open.domain.hisrequest.repository.HisRequestRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author loulan
 * @desc
 */
@Service
public class HisRequestDomainService {
    @Resource
    private HisRequestRepository hisRequestRepository;
    @Resource
    private HisConfig hisConfig;

    public List<HisRequest> queryHisRequest(String transId, String serviceCode) {
        return hisRequestRepository.queryHisRequest(transId,serviceCode);
    }

    public List<HisRequestLog> listAllFiles(String transId){
        return hisRequestRepository.ListAllFiles(transId);
    }

    public HisRequestLog readFileContent(String transIdLogFilePath, String transIdLogFileName){
        return hisRequestRepository.readFileContent(transIdLogFilePath, transIdLogFileName);
    }

    public String getTransLogDir(){
        return hisConfig.getTransLogDir();
    }
}
