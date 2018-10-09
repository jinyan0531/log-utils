package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegeditMeta;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

/**
 * 协议对象
 */
public class Content{

    /**
     * 注册
     * REGEDIT
     *
     *
     * 内容
     * CONTENT
     *
     * 确认信息
     * ACK
     *
     * 心跳
     * HEARTBEAT
     *
     * 销毁
     * DESTROY
     *
     */
    String className;

    String MessageJson;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMessageJson() {
        return MessageJson;
    }

    public void setMessageJson(String messageJson) {
        MessageJson = messageJson;
    }
}
