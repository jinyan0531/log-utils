package com.dy.components.logs.api.communication;

import java.util.Objects;


/**
 * 注册信息
 * @author fufeijian
 */
public class RegeditMeta {
    /**
     * 地址
     */
    String host;

    /**
     * 端口
     */
    int port;

    // 组别
    private String group;
    // 服务名
    private String serviceProviderName;
    // 版本信息
    private String version;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegeditMeta that = (RegeditMeta) o;
        return port == that.port &&
                Objects.equals(host, that.host) &&
                Objects.equals(group, that.group) &&
                Objects.equals(serviceProviderName, that.serviceProviderName) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, group, serviceProviderName, version);
    }

    @Override
    public String toString() {
        return "RegeditMeta{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", group='" + group + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
