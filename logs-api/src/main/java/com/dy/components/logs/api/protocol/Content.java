package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

/**
 * 协议对象
 */
public class Content{

    String className;

    long indexId;

    byte[] collectLog;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getIndexId() {
        return indexId;
    }

    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }

    public byte[] getCollectLog() {
        return collectLog;
    }

    public void setCollectLog(byte[] collectLog) {
        this.collectLog = collectLog;
    }
}
