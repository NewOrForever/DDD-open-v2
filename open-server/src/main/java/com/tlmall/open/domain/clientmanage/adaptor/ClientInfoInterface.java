package com.tlmall.open.domain.clientmanage.adaptor;

/**
 * @author loulan
 * @desc 供外部领域使用的接口
 */
public interface ClientInfoInterface {

    String getPrivateKey(String sysId);

    boolean sendHttpResponse(String sysId, String message);

    boolean isContainSys(String sysId);

}
