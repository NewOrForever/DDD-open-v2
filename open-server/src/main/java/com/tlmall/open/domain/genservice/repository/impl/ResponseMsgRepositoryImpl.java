package com.tlmall.open.domain.genservice.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import com.tlmall.open.domain.genservice.repository.ResponseMsgRepository;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import org.springframework.stereotype.Component;

/**
 * @author ：楼兰
 * @date ：Created in 2021/7/27
 * @description:
 **/

@Component
public class ResponseMsgRepositoryImpl implements ResponseMsgRepository {
    @Override
    public HisRequest generateGsHisRequest(SimpleRequestMsgHeader requestMsgHeader, SimpleRequestMsg requestMsg, SimpleResponseMsg simpleResponseMsg) {
        HisRequest gsHisRequest = new HisRequest();
        gsHisRequest.setTransid(requestMsgHeader.getTransId());
        gsHisRequest.setSysid(requestMsgHeader.getSysId());
        gsHisRequest.setServicecode(requestMsgHeader.getServiceCode());
        gsHisRequest.setReqbody(JSONObject.toJSONString(requestMsg));
        simpleResponseMsg.initRspTime();
        gsHisRequest.setRspbody(JSONObject.toJSONString(simpleResponseMsg));
        gsHisRequest.setIntime(requestMsgHeader.getInTime());
        gsHisRequest.setRsptime(requestMsgHeader.getRespTime());
        return gsHisRequest;
    }
}
