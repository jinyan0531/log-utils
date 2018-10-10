package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

public abstract class AbstractChannel {

    /**
     * 发送日志
     * @param defaultCollectLog
     */
    public abstract void send(DefaultCollectLog defaultCollectLog);

}
