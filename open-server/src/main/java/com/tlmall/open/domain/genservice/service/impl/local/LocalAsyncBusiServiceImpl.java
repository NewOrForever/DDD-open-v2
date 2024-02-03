package com.tlmall.open.domain.genservice.service.impl.local;

import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.clientmanage.adaptor.ClientInfoInterface;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import com.tlmall.open.domain.genservice.adaptor.GsService;
import com.tlmall.open.domain.genservice.repository.BusiServiceRepository;
import com.tlmall.open.domain.genservice.repository.ResponseMsgRepository;
import com.tlmall.open.domain.genservice.service.AsyncBusiService;
import com.tlmall.open.domain.hisrequest.adaptor.HisRequestInterface;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import com.tlmall.open.domain.clientmanage.infrastructure.ThreadPoolHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author loulan
 * @desc
 */
@Service("LocalAsyncBusiService")
public class LocalAsyncBusiServiceImpl implements AsyncBusiService{

    @Resource
    BusiServiceRepository busiServiceRepository;

    @Resource
    ClientInfoInterface clientInfoInterface;

    @Resource
    HisRequestInterface hisRequestInterface;

    @Autowired
    ResponseMsgRepository responseMsgRepository;

    public void dealMessageAsync(SimpleRequestMsg requestMsg,Logger logger){
        SimpleRequestMsgHeader reqHeader = requestMsg.getRequestMsgHeader();
        logger.info("GsRestInterface: 请求验证通过 request=> " + requestMsg);
        //报文检查通过后，同步返回请求接收成功的响应，异步推送业务处理结果
        final List<HisRequest> hisRequests = hisRequestInterface.queryHisRequest(reqHeader.getTransId(), reqHeader.getServiceCode());
        String hisResponse = "";
        if (null != hisRequests && !hisRequests.isEmpty()) {
            //重复的请求，直接推送之前的响应结果。
            hisResponse = hisRequests.get(0).getRspbody();
            logger.info("GsRestInterface: 查询到历史请求记录，响应结果：hisResponse => " + hisResponse);
            clientInfoInterface.sendHttpResponse(reqHeader.getSysId(), hisResponse);
        } else {
            //处理实际业务
            this.doBusi(requestMsg,logger);
        }
    }

    private void doBusi(SimpleRequestMsg requestMsg, Logger logger){
        final SimpleRequestMsgHeader requestMsgHeader = requestMsg.getRequestMsgHeader();
        final JSONObject requestMsgBody = requestMsg.getRequestMsgBody();

        GsService gsService = busiServiceRepository.getGsService(requestMsgHeader.getServiceCode());
        if(null == gsService){
            logger.error("ServiceCode["+requestMsgHeader.getServiceCode()+"] is not support yet.");
            return;
        }

        ThreadPoolHolder.callBusiExecutor.execute(()->{
            logger.info("busi porcess entry => "+requestMsgHeader.getServiceCode());
            final JSONObject busiRes = gsService.doBusi(requestMsg);
            logger.info("busi Res => "+busiRes);

            SimpleResponseMsg simpleResponseMsg = new SimpleResponseMsg();
            simpleResponseMsg.getHeaderFromRequest(requestMsg);
            simpleResponseMsg.setResponseMsgBody(busiRes.toJSONString());

            HisRequest gsHisRequest = responseMsgRepository.generateGsHisRequest(requestMsgHeader,requestMsg,simpleResponseMsg);
            //跨领域的业务只访问interface，不干预实现细节。
            if(hisRequestInterface.addHisRequest(gsHisRequest)){
                logger.info("history loaded: gsHisRequest => "+gsHisRequest);
            }

            if(clientInfoInterface.sendHttpResponse(requestMsgHeader.getSysId(),JSONObject.toJSONString(simpleResponseMsg))){
                logger.info("callback info sended to sysId => "+requestMsgHeader.getSysId());
            }
        });
    }
}
