package com.dy.components.logs.collect.es;

import com.dy.components.logs.api.log.dy.DySqlPorfarmaceTransactionCollectLog;
import com.dy.components.logs.collect.es.index.IndexTools;
import com.dy.components.logs.collect.es.pool.ElasticSearchPool;
import com.dy.components.logs.collect.es.pool.ElasticSearchPoolConfig;
import com.dy.components.logs.collect.es.pool.HostAndPort;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PoolsTest {
    public final static  void main(String[]s) throws IOException {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("127.0.0.1:9200","127.0.0.1",9200,"http"));
        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
        config.setConnectTimeMillis(8000);
        config.setMaxTotal(100);
        config.setClusterName("elasticsearch");
        config.setNodes(nodes);

        ElasticSearchPool pool = new ElasticSearchPool(config);

        RestHighLevelClient client =pool.getResource();

        IndexTools indexTools = new IndexTools(client);
        indexTools.ManagerIndexBuilder();

        DySqlPorfarmaceTransactionCollectLog dySqlPorfarmaceTransactionCollectLog = new DySqlPorfarmaceTransactionCollectLog();

        GetIndexRequest request = new GetIndexRequest();
        request.indices("log_set_manager_index");
        request.local(false);
        request.humanReadable(true);
        boolean exists = client.indices().exists(request) ;

        System.out.println(exists);
    }
}
