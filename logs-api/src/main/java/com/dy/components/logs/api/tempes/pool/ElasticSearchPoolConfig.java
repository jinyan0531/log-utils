package com.dy.components.logs.api.tempes.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : fufeijian
 * @date : 2018/10/10
 */
public class ElasticSearchPoolConfig extends GenericObjectPoolConfig {

    private long connectTimeMillis;

    private String clusterName;

    Set<HostAndPort> nodes = new HashSet<HostAndPort>();

    public long getConnectTimeMillis() {
        return connectTimeMillis;
    }

    public void setConnectTimeMillis(long connectTimeMillis) {
        this.connectTimeMillis = connectTimeMillis;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Set<HostAndPort> getNodes() {
        return nodes;
    }

    public void setNodes(Set<HostAndPort> nodes) {
        this.nodes = nodes;
    }
}
