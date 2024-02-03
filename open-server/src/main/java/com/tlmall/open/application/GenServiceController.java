package com.tlmall.open.application;

import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.clientmanage.adaptor.ClientInfoInterface;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsgHeader;
import com.tlmall.open.domain.genservice.entity.SimpleResponseMsg;
import com.tlmall.open.domain.genservice.entity.*;
import com.tlmall.open.domain.genservice.service.AsyncBusiService;
import com.tlmall.open.domain.genservice.service.DecryptService;
import com.tlmall.open.domain.genservice.service.MessageCheckService;
import com.tlmall.open.domain.genservice.service.SyncBusiService;
import com.tlmall.open.domain.hisrequest.adaptor.HisRequestInterface;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author roy
 * @desc
 */
@RestController
@RequestMapping(value = "/genSI")
public class GenServiceController {

    private final Logger logger = Logger.getLogger(GenServiceController.class);

    @Resource
    private DecryptService decryptService;
    //TODO 单体架构与微服务架构只需要切换一个服务实现类即可，对各领域的核心是没有影响的。。
//    @Resource(name = "LocalAsyncBusiService") //基于SPI实现的单体架构消息处理服务
    @Resource(name = "NacosAsyncBusiService") //基于Nacos实现的微服务消息处理服务
    private AsyncBusiService asyncBusiService;
    @Resource(name = "LocalSyncBusiService")
//    @Resource(name = "NacosSyncBusiService")
    private SyncBusiService syncBusiService;
    @Resource
    private HisRequestInterface hisRequestInterface;
    @Resource
    private MessageCheckService messageCheckService;

    /**
     * Restful服务接口，内部调试用
     */
    @RequestMapping(value = "/gsInterface", method = RequestMethod.POST)
    public Object gsRestInterface(@RequestBody String requestMessage, HttpServletRequest request) {
        logger.info("GsRestInterface: request=> " + requestMessage);
        JSONObject jRequestMessage = JSONObject.parseObject(requestMessage);
        SimpleRequestMsgHeader reqHeader = jRequestMessage.getObject("header", SimpleRequestMsgHeader.class);
        reqHeader.initIntime();
        JSONObject jRequestBody = jRequestMessage.getJSONObject("body");

        SimpleRequestMsg requestMsg = new SimpleRequestMsg(reqHeader, jRequestBody);

        //检查报文 报文检查不通过就直接返回响应。
        CommonGsResponse response = messageCheckService.checkSimpleMsg(requestMsg);
        if (!response.getResult().equals(CommonGsResponse.RESULT_CODE_SUCCESS)) {
            return response;
        }
        hisRequestInterface.addTransLogAppender(logger,reqHeader.getTransId());
        //处理业务
        asyncBusiService.dealMessageAsync(requestMsg,logger);
        logger.info("GsRestInterface: 返回请求响应  => " + response.toJsonFormat());
        hisRequestInterface.removeTransLogAppender(logger);
        return response;
    }

    /**
     * 异步业务接口，配合客户端使用的正式接口
     */
    @RequestMapping(value = "/gsInterfaceAsync", method = RequestMethod.POST)
    public Object gsServiceAsync(String ftRequestInfo, HttpServletRequest request) {
        logger.info("gsServiceAsync: received Request => " + ftRequestInfo);
        // 报文解密
        SimpleRequestMsg requestMsg;
        try {
            requestMsg = decryptService.decrypData(ftRequestInfo);
        } catch (DecryptMessageException e) {
            return new CommonGsResponse("","","", CommonGsResponse.RESULT_CODE_PARAM_ERROR,e.getMessage());
        }
        requestMsg.getRequestMsgHeader().initIntime();

        CommonGsResponse response = messageCheckService.checkSimpleMsg(requestMsg);
        if (!response.getResult().equals(CommonGsResponse.RESULT_CODE_SUCCESS)) {
            return response;
        }
        hisRequestInterface.addTransLogAppender(logger, requestMsg.getRequestMsgHeader().getTransId());
        //处理业务
        asyncBusiService.dealMessageAsync(requestMsg,logger);
        logger.info("gsServiceAsync: 返回请求响应  => " + response.toJsonFormat());
        hisRequestInterface.removeTransLogAppender(logger);
        return response;
    }

    /**
     * 同步业务接口，配合客户端使用的正是接口
     */
    @RequestMapping(value = "/gsServiceSync", method = RequestMethod.POST)
    public Object gsServiceSync(String ftRequestInfo, HttpServletRequest request) {
        logger.info("gsServiceSync: received Request => " + ftRequestInfo);
        // 报文解密
        SimpleRequestMsg requestMsg;
        try {
            requestMsg = decryptService.decrypData(ftRequestInfo);
        } catch (DecryptMessageException e) {
            return new CommonGsResponse("","","", CommonGsResponse.RESULT_CODE_PARAM_ERROR,e.getMessage());
        }
        requestMsg.getRequestMsgHeader().initIntime();

        CommonGsResponse response = messageCheckService.checkSimpleMsg(requestMsg);
        if (!response.getResult().equals(CommonGsResponse.RESULT_CODE_SUCCESS)) {
            return response;
        }
        hisRequestInterface.addTransLogAppender(logger, requestMsg.getRequestMsgHeader().getTransId());
        //处理业务
        final SimpleResponseMsg simpleResponseMsg = syncBusiService.dealMessageSync(requestMsg,logger);
        logger.info("gsServiceSync: 返回请求响应  => " + simpleResponseMsg);
        return simpleResponseMsg;
    }
}
