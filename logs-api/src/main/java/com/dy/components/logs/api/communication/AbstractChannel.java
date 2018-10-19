package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.protocol.Message;

import java.nio.channels.Channel;

public abstract class AbstractChannel implements Channel {

    /**
     * 发送日志
     * @param message
     */
    public abstract void send(Message message);

    public static String DEFAULT_RABBIT_QUEUE = "LOGS_DEFAULT_RABBIT_QUEUE";


}
