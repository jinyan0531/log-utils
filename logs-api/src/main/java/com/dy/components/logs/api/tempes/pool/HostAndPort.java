package com.dy.components.logs.api.tempes.pool;

/**
 * @author : fufeijian
 * @date : 2018/10/10
 * @description
 */
public class HostAndPort {

    private String nodeName;
    private String host ;
    private int port ;
    private String schema;

    public HostAndPort(String nodeName, String host, int port, String schema) {
        this.nodeName = nodeName;
        this.host = host;
        this.port = port;
        this.schema = schema;
    }

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

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
