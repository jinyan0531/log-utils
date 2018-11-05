package com.dy.components.logs.api.search;

import com.dy.components.logs.api.result.PageBean;
import com.dy.components.logs.api.result.ResourceClickCount;
import com.dy.components.logs.api.result.ResourcesClickDeail;
import com.dy.components.logs.api.tempes.pool.ElasticSearchPool;
import com.dy.components.logs.api.tempes.pool.ElasticSearchPoolConfig;
import com.dy.components.logs.api.tempes.pool.HostAndPort;
import com.dy.components.logs.utils.ObjectCastUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;


/**
 *
 */
public class CRMESsearch {

    String host;
    String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    ElasticSearchPool pool = null;


    public void init(){
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort(host+":"+port,host,Integer.parseInt(port),"http"));
        ElasticSearchPoolConfig config = new ElasticSearchPoolConfig();
        config.setConnectTimeMillis(8000);
        config.setMaxTotal(100);
        config.setClusterName("elasticsearch");
        config.setNodes(nodes);
         pool = new ElasticSearchPool(config);
    }


    private BoolQueryBuilder crmRsQueryBuilder(){
        /**
         * 查询CRM及QUERY中URL带有CRM_标记的URL
         */
        MatchQueryBuilder mqsysid = QueryBuilders.matchQuery("logId.sysId","query");
        //MatchQueryBuilder mohu = QueryBuilders.matchQuery("url","*query*");
        WildcardQueryBuilder mohu = QueryBuilders.wildcardQuery("url","*crm_*");
        QueryBuilder query = QueryBuilders.boolQuery().must(mqsysid).must(mohu);
        BoolQueryBuilder bqb = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("logId.sysId","crm")
                ).should(mqsysid);
        return bqb;

    }

    //汇总功能统计
    public List<ResourceClickCount> findResourceClickCount(String userId, long startTime, long endTime, long startCasttime, long endCastTime){

        RestHighLevelClient restHighLevelClient = pool.getResource();

        RestHighLevelClient client =pool.getResource();
        SearchRequest searchRequest = new SearchRequest("dymvcporfarmacetransactioncollectlog_v1.0.0");


        /**
         * 类型选择
         */
        QueryBuilder qb = QueryBuilders.boolQuery().must(crmRsQueryBuilder());
        BoolQueryBuilder allQuery = QueryBuilders.boolQuery();

        QueryBuilder useridQuery = null;
        if(endTime==0) {
            endTime = 1541140766086L;
        }

        if(userId!=null){
            useridQuery = QueryBuilders.matchQuery("userId",userId);
            allQuery.must(useridQuery);
        }
        QueryBuilder timeRangeBuilder = QueryBuilders.rangeQuery("startTime")
                .from(startTime)
                .to(endTime)
                .includeLower(true)     // 包含上界
                .includeUpper(true);      // 包含下届
        allQuery.must(timeRangeBuilder);

        if(endCastTime==0) {
            endCastTime = 9999999999999L;
        }
        QueryBuilder castRangeBuilder = QueryBuilders.rangeQuery("durationTime")
                .from(startCasttime)
                .to(endCastTime)
                .includeLower(true)     // 包含上界
                .includeUpper(true);      // 包含下届
        allQuery.must(castRangeBuilder);


        allQuery.must(qb);


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


        AggregationBuilder tb=  AggregationBuilders.terms("group_url").field("url").size(1000);
        sourceBuilder.aggregation(tb);
        sourceBuilder.query(allQuery);
        sourceBuilder.size(5).from(0);


        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Aggregations aggregations = searchResponse.getAggregations();

        List<ResourceClickCount> list = new ArrayList<>();
        Terms byProvince = aggregations.get("group_url");
        for (Terms.Bucket bucket:byProvince.getBuckets()){
            long value = bucket.getDocCount();

            ResourceClickCount resourceClickCount = new ResourceClickCount();
            resourceClickCount.setCount(bucket.getDocCount());
            resourceClickCount.setUrl((String) bucket.getKey());
            list.add(resourceClickCount);
        }
        return list;

    }

    public PageBean findClickDeail(String url, String userId, long startTime, long endTime, long startCasttime, long endCastTime , int pageNum, int pageSize){
        RestHighLevelClient restHighLevelClient = pool.getResource();



        RestHighLevelClient client =pool.getResource();
        SearchRequest searchRequest = new SearchRequest("dymvcporfarmacetransactioncollectlog_v1.0.0");


        /**
         * 查询出所有资源
         */
        QueryBuilder qb = QueryBuilders.boolQuery().must(crmRsQueryBuilder());
        QueryBuilder useridQuery = null;
        QueryBuilder urlQuery = null;
        BoolQueryBuilder allQuery = QueryBuilders.boolQuery();

        List<QueryBuilder> queryList = new ArrayList<QueryBuilder>();

        QueryBuilder timeRange = null;
        if(endTime==0) {
           endTime = 999999999;
        }

        if(userId!=null){
            useridQuery = QueryBuilders.matchQuery("userId",userId);
            queryList.add(useridQuery);


        }

        if(url!=null){
            urlQuery = QueryBuilders.matchQuery("url",url);

            allQuery.must(urlQuery);

        }

        QueryBuilder timeRangeBuilder = QueryBuilders.rangeQuery("startTime")
                .from(startTime)
                .to(endTime)
                .includeLower(true)     // 包含上界
                .includeUpper(true);      // 包含下届
        allQuery.must(timeRangeBuilder);

        if(endCastTime==0) {
            endCastTime = 9999999999999L;
        }
        QueryBuilder castRangeBuilder = QueryBuilders.rangeQuery("durationTime")
                .from(startCasttime)
                .to(endCastTime)
                .includeLower(true)     // 包含上界
                .includeUpper(true);      // 包含下届
        allQuery.must(castRangeBuilder);

        int size = queryList.size();

        allQuery.must(qb);

        if(pageSize==0){
            pageSize = 100;
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();


        sourceBuilder.query(allQuery);

        sourceBuilder.from(pageNum*pageSize);

        sourceBuilder.size(pageSize);



        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //构建页面

        PageBean<ResourcesClickDeail> pageBean = new PageBean<>();

        pageBean.setCurrPage(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(searchResponse.getHits().getTotalHits());

         SearchHits hits = searchResponse.getHits();
         List<ResourcesClickDeail> list = new ArrayList<>();
        for (SearchHit searchHit : hits) {
            Map<String, Object> source = searchHit.getSourceAsMap();

            ResourcesClickDeail deail = new ResourcesClickDeail();

            deail.setUserId(ObjectCastUtils.castToString(source.get("userId")));

            deail.setParams(ObjectCastUtils.castToString(source.get("parames")));

            deail.setCastTime(ObjectCastUtils.castToLong(source.get("durationTime")));

            deail.setStartTime(new Date(ObjectCastUtils.castToLong(source.get("startTime"))));

            deail.setUrl(ObjectCastUtils.castToString(source.get("url")));
            list.add(deail);
        }

        pageBean.setList(list);


        return pageBean;
    }




}
