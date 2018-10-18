package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.DefaultExceptionCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Arrays;

public class DyDefaultExceptionCollectLog extends DefaultExceptionCollectLog {
    public DyDefaultExceptionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
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
        return "DyDefaultExceptionCollectLog{" +
                "version='" + version + '\'' +
                ", params=" + getParams() +
                ", stacks=" + Arrays.toString(getStacks()) +
                ", startTime=" + getStartTime() +
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

    public LogerBuilder toXContentBuilder(XContentBuilder builder) {
        return null;
    }
}
