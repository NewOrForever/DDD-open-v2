package com.tlmall.open.domain.hisrequest.repository;

import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import com.tlmall.open.domain.hisrequest.entity.HisRequestLog;

import java.util.List;

/**
 * @author loulan
 * @desc
 */
public interface HisRequestRepository {
    List<HisRequest> queryHisRequest(String transId, String serviceCode);

    int addHisRequest(HisRequest gsRequest);

    List<HisRequestLog> ListAllFiles(String transId);

    HisRequestLog readFileContent(String transIdLogFilePath, String transIdLogFileName);
}
