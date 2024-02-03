package com.tulingmall.service.mobilearea.adaptor;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author loulan
 * @desc 响应开放平台的服务请求。
 */
@RestController
public class MobileAreaController {

    private final Logger logger = LoggerFactory.getLogger(MobileAreaController.class);

    @RequestMapping(value = "/open/service",method = RequestMethod.POST)
    public Object getMobileTag(@RequestBody String requestJson){
        Map<String,Object> response = new HashMap<>();
        logger.info("received request:"+requestJson);
        JSONObject requestObj = JSONObject.parseObject(requestJson);
        String mobile = requestObj.getString("mobile");
        response.put("mobile",mobile);

        String tagSearchUrl = "https://www.sogou.com/websearch/phoneAddress.jsp?phoneNumber=${mobile}&cb=handlenumber&isSogoDomain=0";
        try {
            String httpRes = HttpUtil.get(tagSearchUrl.replace("${mobile}", mobile));
            String mobileArea = httpRes.substring(httpRes.indexOf("(")+1,httpRes.lastIndexOf(")")).replace("\"", "").replace("\\\\", "\\");
            response.put("mobileArea",mobileArea);
        } catch (Exception e) {
            response.put("error","查询出错，请检查服务或重新查询");
        }
        return response;
    }
}
