package com.dy.components.logs.api.logs;

import com.dy.components.logs.api.collect.LogsEnum;

import java.io.Serializable;
import java.util.List;

/**
 * 所有日志类都继承DefaultLogs
 */
public class DefaultLogs implements Serializable {


    /**
     * Log标记
     */
    String LogId;

    /**
     * 服务器ip
     */
    Integer ip;


    /**
     * 服务器名
     */
    String hostName;


    /**
     * 应用名
     */
    String sysId;


    /**
     * 是否是入口
     */
    Boolean isInlet;

    /**
     * 深度
     */
    Integer deepLevel;

    /**
     *  日志类型
     */
    LogsEnum logType;


    //时间戳
    Long timespan;


    /**
     * 子日志
     */
    List<DefaultLogs> childsLogsList;


    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public Long getTimespan() {
        return timespan;
    }

    public void setTimespan(Long timespan) {
        this.timespan = timespan;
    }

    public String getLogId() {
        return LogId;
    }

    public void setLogId(String logId) {
        LogId = logId;
    }

    public LogsEnum getLogType() {
        return logType;
    }

    public void setLogType(LogsEnum logType) {
        this.logType = logType;
    }

    public Integer getDeepLevel() {
        return deepLevel;
    }

    public void setDeepLevel(Integer deepLevel) {
        this.deepLevel = deepLevel;
    }


    public Boolean getInlet() {
        return isInlet;
    }

    public void setInlet(Boolean inlet) {
        isInlet = inlet;
    }

    public List<DefaultLogs> getChildsLogsList() {
        return childsLogsList;
    }

    public void setChildsLogsList(List<DefaultLogs> childsLogsList) {
        this.childsLogsList = childsLogsList;
    }
}
