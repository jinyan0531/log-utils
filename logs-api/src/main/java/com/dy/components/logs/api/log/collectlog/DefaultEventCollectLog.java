package com.dy.components.logs.api.log.collectlog;

public class DefaultEventCollectLog extends  DefaultCollectLog {
    public DefaultEventCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }
}
