package com.dy.components.logs.api.log.collectlog;

import java.util.Map;

/**
 * @author fufeijian newone1997@gmail.com
 */
public class DefaultExceptionCollectLog extends  DefaultCollectLog{
    public DefaultExceptionCollectLog(String message, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, firstLogId, parentLogId, logId);
    }

    /**
     * 方法参数
     */
    Map<String, Object> params;

    /**
     * 异常堆栈信息
     */
    StackTraceElement[] stacks;

    /**
     * 日志时间
     */
    long startTime;
}
