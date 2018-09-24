package com.dy.components.logs.api.communication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public abstract class DefaultClient {
    /**
     * 地址
     */
    String host;
    /**
     * 端口
     */
    int port;

    private String username                       = "";
    private String password                       = "";

    /**
     * 最大请求Channel
     */
    int requestedChannelMax;

    /**
     * 心跳频率
     */
    int requestedHeartbeat;

    /**
     * 连接超时
     */
    int connectionTimeout;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRequestedChannelMax() {
        return requestedChannelMax;
    }

    public void setRequestedChannelMax(int requestedChannelMax) {
        this.requestedChannelMax = requestedChannelMax;
    }

    public int getRequestedHeartbeat() {
        return requestedHeartbeat;
    }

    public void setRequestedHeartbeat(int requestedHeartbeat) {
        this.requestedHeartbeat = requestedHeartbeat;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
