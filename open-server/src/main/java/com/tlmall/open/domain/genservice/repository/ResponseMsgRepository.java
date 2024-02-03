package com.tlmall.open.domain.genservice.repository;

import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;

/**
 * @author ：楼兰
 * @date ：Created in 2021/7/27
 * @description:
 **/

public interface ResponseMsgRepository {
    HisRequest generateGsHisRequest(SimpleRequestMsgHeader requestMsgHeader, SimpleRequestMsg requestMsg, SimpleResponseMsg simpleResponseMsg);
}
