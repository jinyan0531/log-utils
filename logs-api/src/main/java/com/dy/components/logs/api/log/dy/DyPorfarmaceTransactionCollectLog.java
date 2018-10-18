package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class DyPorfarmaceTransactionCollectLog extends DefaulPorfarmaceTransactionCollectLog {
    public DyPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }
    String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DyPorfarmaceTransactionCollectLog{" +
                "version='" + version + '\'' +
                ", durationTime=" + durationTime +
                ", endTime=" + endTime +
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
    public XContentBuilder toXContentBuilder() {
        return null;
    }
}
