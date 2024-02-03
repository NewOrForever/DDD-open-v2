package com.tlmall.open.domain.genservice.service;

import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import org.apache.log4j.Logger;

/**
 * @author loulan
 * @desc
 */
public interface SyncBusiService {
    SimpleResponseMsg dealMessageSync(SimpleRequestMsg requestMsg, Logger logger);
}
