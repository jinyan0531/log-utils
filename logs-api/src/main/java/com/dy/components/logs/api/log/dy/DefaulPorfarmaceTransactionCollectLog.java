package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.collectlog.DefaultTransactionCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class DefaulPorfarmaceTransactionCollectLog extends DefaultTransactionCollectLog {
    public DefaulPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }

    long durationTime;

    long endTime;

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "DefaulPorfarmaceTransactionCollectLog{" +
                "durationTime=" + durationTime +
                ", endTime=" + endTime +
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
