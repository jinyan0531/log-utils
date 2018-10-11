package com.dy.components.logs.collect.es.pool;

import org.elasticsearch.client.RestHighLevelClient;

import java.util.Set;

/**
 * @author : fufeijian
 */
public class ElasticSearchPool extends Pool<RestHighLevelClient> {

    private String clusterName;
    private Set<HostAndPort> clusterNodes;

    public ElasticSearchPool(ElasticSearchPoolConfig config){
        super(config, new ElasticSearchClientFactory(config.getClusterName(), config.getNodes()));
        this.clusterName = clusterName;
        this.clusterNodes = clusterNodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Set<HostAndPort> getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(Set<HostAndPort> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }



}
