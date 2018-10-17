package com.dy.components.logs.collect.es.index;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
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


    static final String defaultManagerIndexIdType = "logmanager";

    XContentBuilder builder;

    AppendBuilder appenduilder;



    public IndexTools(RestHighLevelClient client){
        this.client = client;
    }


    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(){
        ManagerIndexBuilder(null,null);
    }
    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(AppendBuilder appenduilder){

        ManagerIndexBuilder(null,appenduilder);

    }
    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(XContentBuilder newBuilder){

        ManagerIndexBuilder(newBuilder,null);

    }
    /**
     * 创建管理 索引
     */
    public void ManagerIndexBuilder(XContentBuilder newBuilder,AppendBuilder appenduilder){

        this.builder = newBuilder;
        this.appenduilder = appenduilder;
        buildDefauletIndex();

    }




    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(AppendBuilder appenduilder){

        this.appenduilder = appenduilder;
        LogIndexBuilder(null,appenduilder);

    }
    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(XContentBuilder newBuilder){

        this.builder = newBuilder;
        LogIndexBuilder(newBuilder,null);

    }
    /**
     * 创建日志索引
     */
    public void LogIndexBuilder(XContentBuilder newBuilder,AppendBuilder appenduilder){

        this.builder = newBuilder;
        this.appenduilder = appenduilder;



    }



    private void  buildDefauletIndex(){
        /**
         * 判断索引是否存在
         */
        if(!checkIndexExist(defaultManagerIndex)){

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


    public boolean checkIndexExist(String index) {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
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


       final static String DEFAULT_LOG_TYPE="default_log";

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



        /**
         * 构造通用日志索引
         * @return
         */
        public static XContentBuilder  defaultLogMapping(AppendBuilder appenduilder){

            try {
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {

                    builder.startObject(DEFAULT_LOG_TYPE);
                    {
                        builder.startObject("properties");
                        {

                            //属性
                            builder.startObject("message");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();

                            //属性
                            builder.startObject("messageTempletId");
                            {
                                builder.field("type", "long");
                            }
                            builder.endObject();

                            //结束标记

                            builder.startObject("isEnd");
                            {
                                builder.field("type", "boolean");
                            }
                            builder.endObject();

                            //起始标记
                            builder.startObject("isFirst");
                            {
                                builder.field("type", "boolean");
                            }
                            builder.endObject();

                            //类型
                            builder.startObject("logType");
                            {
                                builder.field("type", "keyword");
                            }
                            builder.endObject();

                            builder.startObject("logId");
                            {
                                LoginIdBuild(builder);
                            }

                            builder.endObject();
                            builder.startObject("parentLogId");
                            {
                                LoginIdBuild(builder);
                            }

                            builder.endObject();
                            builder.startObject("firstLogId");
                            {
                                LoginIdBuild(builder);
                            }

                            builder.endObject();

                            /**
                             * 添加构建
                             */
                            if(appenduilder!=null){
                                appenduilder.builder();
                            }
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
            return  null;

        }


        private static XContentBuilder LoginIdBuild(XContentBuilder builder) throws IOException {

            builder.field("type", "nested");
            builder.startObject("properties");
            {
                //IP
                builder.startObject("ip");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                //地址名
                builder.startObject("hostName");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                //ID
                builder.startObject("id");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                //系统ID
                builder.startObject("sysId");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

            }
            builder.endObject();

            return builder;
        }



    }





}
