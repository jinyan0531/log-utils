package com.dy.components.logs.api.log.collectlog;

import java.util.List;

public class DefaultTransactionCollectLog extends  DefaultCollectLog {
    public DefaultTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }

    String parames;

    String name;


    long startTime;

    List<DefaultCollectLog> childrenLogs;

    public String getParames() {
        return parames;
    }

    public void setParames(String parames) {
        this.parames = parames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public List<DefaultCollectLog> getChildrenLogs() {
        return childrenLogs;
    }

    public void setChildrenLogs(List<DefaultCollectLog> childrenLogs) {
        this.childrenLogs = childrenLogs;
    }

    @Override
    public String toString() {
        return "DefaultTransactionCollectLog{" +
                "parames='" + parames + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", childrenLogs=" + childrenLogs +
                ", logId=" + logId +
                ", parentLogId=" + parentLogId +
                ", firstLogId=" + firstLogId +
                ", message='" + message + '\'' +
                ", messageTempletId=" + messageTempletId +
                ", isCompleted=" + isCompleted +
                ", isEnd=" + isEnd +
                ", isFirst=" + isFirst +
                ", logType='" + logType + '\'' +
                '}';
    }
}
