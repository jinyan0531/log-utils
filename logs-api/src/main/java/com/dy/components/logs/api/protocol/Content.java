package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

import java.util.Arrays;

/**
 * 协议对象
 */
public class Content{

    String logType;

    String className;

    long indexId;

    String indexVersion;


    byte[] collectLog;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getIndexVersion() {
        return indexVersion;
    }

    public void setIndexVersion(String indexVersion) {
        this.indexVersion = indexVersion;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Content{" +
                "logType='" + logType + '\'' +
                ", className='" + className + '\'' +
                ", indexId=" + indexId +
                ", indexVersion='" + indexVersion + '\'' +
                ", collectLog=" + Arrays.toString(collectLog) +
                '}';
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
