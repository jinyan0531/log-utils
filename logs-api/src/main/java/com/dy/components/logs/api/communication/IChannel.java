package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

public interface IChannel {

    /**
     * 发送日志
     * @param defaultCollectLog
     */
    void send(DefaultCollectLog defaultCollectLog);

}
