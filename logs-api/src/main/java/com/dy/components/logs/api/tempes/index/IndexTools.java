package com.dy.components.logs.api.tempes.index;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author fufeijian
 */

public class IndexTools{
    Logger logger = LoggerFactory.getLogger(IndexTools.class);

    RestHighLevelClient client;

    static final String defaultManagerIndex = "log_set_manager_index";
    static final String defaultManagerIndexId = "100001";


    static final String defaultManagerIndexIdType = "log_manager";

    static final String defaultLogSetIndex = "log_set_index";

   ;

    public IndexTools(RestHighLevelClient client){

        this.client = client;
    }




    public IndexRequest getLogManagerRequest(){

        if (checkIndexExist(defaultManagerIndex,defaultManagerIndexIdType)){
            return new IndexRequest(defaultManagerIndex, defaultManagerIndexIdType, defaultManagerIndexId);
        }else {
            return null;

        }
    }
    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(){
        ManagerIndexBuilder(null);
    }

    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(String newBuilder){
        buildDefauletManagerIndex(newBuilder);

    }


    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(String builder,String className,String indexId){
       buildDefauletLogIndex(builder,className,indexId);
    }

    public IndexRequest getLogIndexRequest(String builder,String className,String indexId,String indexVersion){

        String indexName = className.toLowerCase()+"_"+indexVersion;

        if(!checkIndexExist(indexName,className)) {
            CreateIndexRequest request = new CreateIndexRequest(indexName);

            request.mapping(className, builder, XContentType.JSON);
            try {
                CreateIndexResponse createIndexResponse = client.indices().create(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        IndexRequest indexRequest = new IndexRequest(indexName, className, indexId);

        indexRequest.source(builder, XContentType.JSON);

        return indexRequest;


    }
    /**
     * 创建索引
     */
    private void buildDefauletLogIndex(String builder,String className,String indexId){
        try {
            if(builder!=null){
                if(!checkIndexExist(className,className)){
                    IndexRequest indexRequest = new IndexRequest(className.toLowerCase(), className, indexId);

                    indexRequest.source(builder, XContentType.JSON);

                    this.client.index(indexRequest,RequestOptions.DEFAULT);

                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private void  buildDefauletManagerIndex( String builder){
        /**
         * 判断索引是否存在
         */
        if(!checkIndexExist(defaultManagerIndex,defaultManagerIndexIdType)){

            IndexRequest indexRequest = new IndexRequest(defaultManagerIndex, defaultManagerIndexIdType, defaultManagerIndexId);

            if(builder==null){
                builder = DefaultBuilder.defaultManagerMapping();
            }

            indexRequest.source(builder, XContentType.JSON);

            try {
                this.client.index(indexRequest,RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public boolean checkIndexExist(String index,String type) {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        if(type!=null){
            request.types(type);
        }

        request.local(false);
        request.humanReadable(true);
        try {
            boolean exists = client.indices().exists(request) ;
            return exists;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }





    private static class DefaultBuilder{


        final static  String DEFAULT_MANAGER_TYPE = "default_manager";


        public static String  defaultManagerMapping(){
            try {
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.startObject(DEFAULT_MANAGER_TYPE);
                    {
                        builder.startObject("properties");
                        {
                            //索引
                            builder.startObject("indexName");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();

                            //索引中文名称
                            builder.startObject("typeName");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();
                            //id
                            builder.startObject("id");
                            {
                                builder.field("type", "long");
                            }
                            builder.endObject();
                            //描述
                            builder.startObject("describe");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();

                            //创建时间
                            builder.startObject("createTime");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();
                        }
                        builder.endObject();

                    }
                    builder.endObject();
                }
                builder.endObject();


                return Strings.toString(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }





}
