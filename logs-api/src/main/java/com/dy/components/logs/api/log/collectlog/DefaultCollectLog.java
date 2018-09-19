package com.dy.components.logs.api.log.collectlog;

/**
 * 采集日志父类
 * @author fufeijian newone1997@gmail.com
 */
public class DefaultCollectLog {
    /**
     * 唯一ID
     */
    LogId logId;

    /**
     * 父类的Logid
     */
    LogId parentLogId;

    /**
     * 第一个Logid
     */
    LogId firstLogId;

    /**
     * 内容
     */
    String message;

    /**
     * 是否已经完成
     */
    boolean isCompleted;

    /**
     * 类型
     */
    String logType = "DefaultCollectLog";


    public DefaultCollectLog(String message,LogId firstLogId,LogId parentLogId,LogId logId){
        this.message = message;
        this.firstLogId = firstLogId;
        this.parentLogId = parentLogId;
        this.logId = logId;
        this.isCompleted = false;
    }


    public void end(){
        this.isCompleted =true;
    }

    public LogId getLogId() {
        return logId;
    }

    public void setLogId(LogId logId) {
        this.logId = logId;
    }

    public LogId getParentLogId() {
        return parentLogId;
    }

    public void setParentLogId(LogId parentLogId) {
        this.parentLogId = parentLogId;
    }

    public LogId getFirstLogId() {
        return firstLogId;
    }

    public void setFirstLogId(LogId firstLogId) {
        this.firstLogId = firstLogId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
