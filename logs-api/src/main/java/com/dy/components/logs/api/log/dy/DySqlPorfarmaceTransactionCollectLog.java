package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.collectlog.LogId;

public class DySqlPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog{
    public DySqlPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }
    String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "DySqlPorfarmaceTransactionCollectLog{" +
                "sql='" + sql + '\'' +
                ", version='" + version + '\'' +
                ", durationTime=" + durationTime +
                ", endTime=" + endTime +
                ", version='" + getVersion() + '\'' +
                ", durationTime=" + getDurationTime() +
                ", endTime=" + getEndTime() +
                ", parames='" + getParames() + '\'' +
                ", name='" + getName() + '\'' +
                ", startTime=" + getStartTime() +
                ", childrenLogs=" + getChildrenLogs() +
                ", logId=" + getLogId() +
                ", parentLogId=" + getParentLogId() +
                ", firstLogId=" + getFirstLogId() +
                ", message='" + getMessage() + '\'' +
                ", completed=" + isCompleted() +
                ", logType='" + getLogType() + '\'' +
                ", end=" + isEnd() +
                ", first=" + isFirst() +
                ", messageTempletId=" + getMessageTempletId() +
                '}';
    }



}
