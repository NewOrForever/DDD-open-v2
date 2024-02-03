package com.tulingmall.service.mobilearea.adaptor;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.genservice.adaptor.GsService;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
/**
 * @author loulan
 * @desc
 */
public class MobileAreaServiceImpl implements GsService {

    @Override
    public JSONObject doBusi(SimpleRequestMsg requestMsg) {
        JSONObject response = new JSONObject();

        final JSONObject requestMsgBody = requestMsg.getRequestMsgBody();
        String mobile = requestMsgBody.getString("mobile");
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

    @Override
    public String serviceCode() {
        return "search_mobile_area";
    }
}
