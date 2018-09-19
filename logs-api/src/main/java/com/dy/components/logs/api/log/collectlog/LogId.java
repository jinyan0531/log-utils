package com.dy.components.logs.api.log.collectlog;

/**
 * 日志ID
 * 每个日志都会有LogId类，记录Log的唯一性
 *  @author fufeijian newone1997@gmail.com
 */
public class LogId {
    /**
     *服务器ip
     */
    String ip;
    /**
     * 服务器名称，域名
     */
    String hostName;
    /**
     * 全局唯一
     */
    String id;

    /**
     * 系统id
     */
    String sysId;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
