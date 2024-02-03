package com.tlmall.open.domain.clientmanage.infrastructure;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：楼兰
 * @description :
 **/

public class ThreadPoolHolder {

    public static ExecutorService pushMessageExecutor = new ThreadPoolExecutor(10, 20, 200L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new CustomizableThreadFactory("thread-call-push-%d"));
//            new ThreadFactoryBuilder().setNameFormat("thread-call-push-%d").build());

    public static ExecutorService callBusiExecutor = new ThreadPoolExecutor(50, 100, 200L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new CustomizableThreadFactory("thread-call-busi-%d"));
//            new ThreadFactoryBuilder().setNameFormat("thread-call-busi-%d").build());
}
