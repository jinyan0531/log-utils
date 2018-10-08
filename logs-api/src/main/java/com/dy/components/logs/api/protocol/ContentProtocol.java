package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegeditMeta;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

/**
 * 协议对象
 */
public class ContentProtocol{

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

    DefaultCollectLog log;





    public DefaultCollectLog getLog() {
        return log;
    }

    public void setLog(DefaultCollectLog log) {
        this.log = log;
    }


    @Override
    public String toString() {
        return "ContentProtocol{" +
                "log=" + log +
                '}';
    }
}
