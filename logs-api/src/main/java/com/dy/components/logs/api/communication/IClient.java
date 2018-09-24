package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;

/**
 * @author fufeijian
 */
public interface IClient {
    /**
     * 创建连接
     * @return
     */
     IClient create(String host, int port);

    /**
     * 发送日志
     * @param defaultCollectLog
     */
     void send(DefaultCollectLog defaultCollectLog);


}
