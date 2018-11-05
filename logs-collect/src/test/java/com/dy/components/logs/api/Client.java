package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.search.CRMESsearch;
import com.dy.components.logs.collect.es.pool.ElasticSearchPool;
import com.dy.components.logs.collect.es.pool.ElasticSearchPoolConfig;
import com.dy.components.logs.collect.es.pool.HostAndPort;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Client {



    public static void main(String[] s) throws IOException {


        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.20.71:9200","192.168.20.71",9200,"http"));
//        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
//        config.setConnectTimeMillis(8000);
//        config.setMaxTotal(100);
//        config.setClusterName("elasticsearch");
//        config.setNodes(nodes);
//
//        ElasticSearchPool pool = new ElasticSearchPool(config);
//
//        RestHighLevelClient client =pool.getResource();
//        SearchRequest searchRequest = new SearchRequest("dymvcporfarmacetransactioncollectlog_v1.0.0");
//
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//
//
//
//        AggregationBuilder tb=  AggregationBuilders.terms("group_url").field("url").size(10000);
//       // AggregationBuilder tb2=  AggregationBuilders.terms("group_uri").field("uri");
//        sourceBuilder.aggregation(tb);
//        //sourceBuilder.aggregation(tb2);
//        sourceBuilder.size(5).from(0);
//
//
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = client.search(searchRequest);
//        Aggregations aggregations = searchResponse.getAggregations();
//
//        Terms byProvince = aggregations.get("group_url");
//        for (Terms.Bucket bucket:byProvince.getBuckets()){
//            Cardinality count = bucket.getAggregations().get("docCount");
//            long value = bucket.getDocCount();
//            System.out.println(bucket.getKey());
//            System.out.println(value);
//        }


        CRMESsearch crmeSsearch = new CRMESsearch();
        crmeSsearch.setHost("192.168.20.71");
        crmeSsearch.setPort("9200");
        crmeSsearch.init();

       System.out.println(crmeSsearch.findResourceClickCount(null,0,0,0,10000));   ;



    }
}
