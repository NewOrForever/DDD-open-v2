package com.tlmall.open.domain.hisrequest.adaptor;

import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author loulan
 * @desc 供外部领域使用的接口
 */
public interface HisRequestInterface {

    boolean addHisRequest(HisRequest hisRequest);

    void addTransLogAppender(Logger logger, String transId);

    void removeTransLogAppender(Logger logger);

    List<HisRequest> queryHisRequest(String transId, String serviceCode);
}
