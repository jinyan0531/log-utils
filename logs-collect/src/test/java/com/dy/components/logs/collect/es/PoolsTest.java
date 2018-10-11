package com.dy.components.logs.collect.es;

import com.dy.components.logs.collect.es.pool.ElasticSearchPool;
import com.dy.components.logs.collect.es.pool.ElasticSearchPoolConfig;
import com.dy.components.logs.collect.es.pool.HostAndPort;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PoolsTest {
    public final static  void main(String[]s) throws IOException {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.20.70:9200","192.168.20.70",9200,"http"));
        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
        config.setConnectTimeMillis(8000);
        config.setMaxTotal(100);
        config.setClusterName("elasticsearch");
        config.setNodes(nodes);
        ElasticSearchPool pool = new ElasticSearchPool(config);

        RestHighLevelClient client =pool.getResource();

        System.out.println(client.ping());

    }
}
