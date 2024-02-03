package com.tlmall.open.domain.genservice.adaptor;

import com.alibaba.fastjson.JSONObject;

/**
 * @author loulan
 * @desc
 */
public interface GsService {

    JSONObject doBusi(SimpleRequestMsg requestMsg);

    String serviceCode();
}
