package com.dy.components.logs.collect.es.index;
import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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

    static final String defaultLogSetIndexId = "100002";


    XContentBuilder builder;
    LogerBuilder logerBuilder;

    public IndexTools(RestHighLevelClient client){

        this.client = client;
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
    public void ManagerIndexBuilder(XContentBuilder newBuilder){

        this.builder = newBuilder;
        buildDefauletManagerIndex();

    }


    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(LogerBuilder logerBuilder){
        this.logerBuilder = logerBuilder;
        LogIndexBuilder(logerBuilder);
    }
    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(){
        LogIndexBuilder(null);
    }


    /**
     * 创建索引
     */
    private void buildDefauletLogIndex(){

        if(logerBuilder!=null){
            if(!checkIndexExist(defaultLogSetIndex,logerBuilder.getType())){
                IndexRequest indexRequest = new IndexRequest(defaultManagerIndex, logerBuilder.getType(), String.valueOf(logerBuilder.getId()));



                XContentBuilder builder =logerBuilder.builder();

                indexRequest.source(builder);

                try {
                    this.client.index(indexRequest,RequestOptions.DEFAULT);
                }catch (IOException e){
                    e.printStackTrace();
                }




            }
        }

    }


    private void  buildDefauletManagerIndex(){
        /**
         * 判断索引是否存在
         */
        if(!checkIndexExist(defaultManagerIndex,defaultManagerIndexIdType)){

            IndexRequest indexRequest = new IndexRequest(defaultManagerIndex, defaultManagerIndexIdType, defaultManagerIndexId);

            if(builder==null){
                builder = DefaultBuilder.defaultManagerMapping();
            }

            indexRequest.source(builder);

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


        public static XContentBuilder  defaultManagerMapping(){
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
                return builder;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }






    }





}
