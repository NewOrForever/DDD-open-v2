package com.tlmall.open.domain.genservice.service;

import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.entity.CommonGsResponse;
import org.springframework.stereotype.Service;

/**
 * @author loulan
 * @desc
 */
@Service
public interface MessageCheckService {

    CommonGsResponse checkSimpleMsg(SimpleRequestMsg requestMsg);
}
