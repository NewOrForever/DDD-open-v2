package com.tlmall.open.domain.genservice.service;

import com.tlmall.open.domain.genservice.adaptor.SimpleRequestMsg;
import com.tlmall.open.domain.genservice.entity.DecryptMessageException;

/**
 * @author ：楼兰
 * @description: 报文解密服务
 **/

public interface DecryptService {
    //这个地方传的参数最好要封装成一个对象，Domian Primative。 这里偷个懒
    SimpleRequestMsg decrypData(String ftRequestInfo) throws DecryptMessageException;
}
