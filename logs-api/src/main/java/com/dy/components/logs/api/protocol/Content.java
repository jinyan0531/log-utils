package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

/**
 * 协议对象
 */
public class Content{



    String MessageJson;

    public String getMessageJson() {
        return MessageJson;
    }

    public void setMessageJson(String messageJson) {
        MessageJson = messageJson;
    }

    @Override
    public String toString() {
        return "Content{" +
                "MessageJson='" + MessageJson + '\'' +
                '}';
    }
}
