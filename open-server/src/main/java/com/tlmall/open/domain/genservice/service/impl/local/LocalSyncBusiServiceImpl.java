package com.tlmall.open.domain.genservice.service.impl.local;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.genservice.adaptor.GsService;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import com.tlmall.open.domain.genservice.repository.BusiServiceRepository;
import com.tlmall.open.domain.genservice.repository.ResponseMsgRepository;
import com.tlmall.open.domain.genservice.service.SyncBusiService;
import com.tlmall.open.domain.hisrequest.adaptor.HisRequestInterface;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author loulan
 * @desc
 */
@Service("LocalSyncBusiService")
public class LocalSyncBusiServiceImpl implements SyncBusiService{

    @Resource
    private HisRequestInterface hisRequestInterface;
    @Resource
    private ResponseMsgRepository responseMsgRepository;
    @Resource
    private BusiServiceRepository busiServiceRepository;

    @Override
    public SimpleResponseMsg dealMessageSync(SimpleRequestMsg requestMsg,Logger logger) {
        SimpleResponseMsg response = new SimpleResponseMsg();
        SimpleRequestMsgHeader reqHeader = requestMsg.getRequestMsgHeader();
        logger.info("GsRestInterface: 请求验证通过 request=> " + requestMsg);
        //报文检查通过后，同步返回请求接收成功的响应，异步推送业务处理结果
        final List<HisRequest> hisRequests = hisRequestInterface.queryHisRequest(reqHeader.getTransId(), reqHeader.getServiceCode());
        String hisResponse = "";
        if (null != hisRequests && !hisRequests.isEmpty()) {
            //重复的请求，直接推送之前的响应结果。
            hisResponse = hisRequests.get(0).getRspbody();
            logger.info("GsRestInterface: 查询到历史请求记录，响应结果：hisResponse => " + hisResponse);
            response = JSON.parseObject(hisResponse, SimpleResponseMsg.class);
        } else {
            //处理实际业务
            response = this.doBusi(requestMsg,logger);
        }
        return response;
    }

    private SimpleResponseMsg doBusi(SimpleRequestMsg requestMsg, Logger logger) {
        SimpleResponseMsg simpleResponseMsg = new SimpleResponseMsg();
        simpleResponseMsg.getHeaderFromRequest(requestMsg);
        final JSONObject responseBody = new JSONObject();

        final SimpleRequestMsgHeader requestMsgHeader = requestMsg.getRequestMsgHeader();
        GsService gsService = busiServiceRepository.getGsService(requestMsgHeader.getServiceCode());
        if (null == gsService) {
            logger.error("ServiceCode[" + requestMsgHeader.getServiceCode() + "] is not support yet.");
            responseBody.put("error", "ServiceCode[" + requestMsgHeader.getServiceCode() + "] is not support yet.");
            simpleResponseMsg.setResponseMsgBody(responseBody.toJSONString());
            return simpleResponseMsg;
        }

        logger.info("busi porcess entry => " + requestMsgHeader.getServiceCode());
        final JSONObject busiRes = gsService.doBusi(requestMsg);
        logger.info("busi Res => " + busiRes);

        simpleResponseMsg.setResponseMsgBody(busiRes.toJSONString());

        HisRequest gsHisRequest = responseMsgRepository.generateGsHisRequest(requestMsgHeader,requestMsg,simpleResponseMsg);
        hisRequestInterface.addHisRequest(gsHisRequest);

        logger.info("history loaded: gsHisRequest => " + gsHisRequest);
        return simpleResponseMsg;
    }
}
