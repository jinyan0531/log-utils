package com.dy.components.logs.api.log.collectlog;

import java.util.Arrays;
import java.util.Map;

/**
 * @author fufeijian newone1997@gmail.com
 */
public class DefaultExceptionCollectLog extends  DefaultCollectLog{
    public DefaultExceptionCollectLog(String message,long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message,messageTempletId, firstLogId, parentLogId, logId);
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




    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public StackTraceElement[] getStacks() {
        return stacks;
    }

    public void setStacks(StackTraceElement[] stacks) {
        this.stacks = stacks;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }



    @Override
    public String toString() {
        return "DefaultExceptionCollectLog{" +
                "params=" + params +
                ", stacks=" + Arrays.toString(stacks) +
                ", startTime=" + startTime +
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
