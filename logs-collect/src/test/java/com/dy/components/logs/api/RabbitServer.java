package com.dy.components.logs.api;


import com.dy.components.logs.collect.es.pool.ElasticSearchPool;
import com.dy.components.logs.collect.es.pool.ElasticSearchPoolConfig;
import com.dy.components.logs.collect.es.pool.HostAndPort;
import com.dy.components.logs.collect.server.rabbitmq.DefaultRabbitServer;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RabbitServer extends DefaultRabbitServer {
    public RabbitServer(String host, int port, String user, String password) {
        super(host, port, user, password);
    }

    public static void main(String[] s) throws IOException {

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.20.71:9200","192.168.20.71",9200,"http"));
        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
        config.setConnectTimeMillis(8000);
        config.setMaxTotal(100);
        config.setClusterName("elasticsearch");
        config.setNodes(nodes);

        ElasticSearchPool pool = new ElasticSearchPool(config);

        RestHighLevelClient client =pool.getResource();


        RabbitServer rabbitServer = new RabbitServer("192.168.20.51",30702,"rabbit","123456");

        //RabbitServer rabbitServer = new RabbitServer("127.0.0.1",5672,"guest","guest");


        rabbitServer.startRegistryServer(client);
    }
}
