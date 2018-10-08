package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

/**
 * 协议对象
 */
public class CommuniObject {

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
    String type;
    DefaultCollectLog log;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DefaultCollectLog getLog() {
        return log;
    }

    public void setLog(DefaultCollectLog log) {
        this.log = log;
    }
}
