package com.tulingmall.service.mobiletag.adaptor;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.tlmall.open.domain.genservice.adaptor.GsService;
import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tulingmall.service.mobiletag.util.CodeUtil;

/**
 * @author loulan
 * @desc
 */
public class MobileTagServiceImpl implements GsService {
    @Override
    public JSONObject doBusi(SimpleRequestMsg requestMsg) {
        JSONObject response = new JSONObject();

        final JSONObject requestMsgBody = requestMsg.getRequestMsgBody();
        String mobile = requestMsgBody.getString("mobile");
        response.put("mobile",mobile);
        String tagSearchUrl = "https://www.sogou.com/reventondc/inner/vrapi?number=${mobile}&callback=show&isSogoDomain=0&objid=10001001";
        try {
            String httpRes = HttpUtil.get(tagSearchUrl.replace("${mobile}", mobile));
            String mobileTag = CodeUtil.decodeUnicode(httpRes.substring(httpRes.indexOf(":") + 1, httpRes.indexOf(",")).replace("\"", "").replace("\\\\", "\\"));
            response.put("mobileTag",mobileTag);
        } catch (Exception e) {
            response.put("error","查询出错，请检查服务或重新查询");
        }
        return response;
    }

    @Override
    public String serviceCode() {
        return "search_mobile_tag";
    }
}
