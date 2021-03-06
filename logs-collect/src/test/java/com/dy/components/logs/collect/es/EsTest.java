package com.dy.components.logs.collect.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EsTest {
    private static String host="192.168.20.70"; // 服务器地址

    private static int port=9200; // 端口

    private TransportClient client=null;

    /**
     * 获取连接
     * @return
     */
    @SuppressWarnings({ "unchecked", "resource" })
    @Before
    public void getCient()throws Exception{
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(EsTest.host), EsTest.port));
    }

    /**
     * 关闭连接
     * @param
     */
    @After
    public void close(){
        if(client!=null){
            client.close();
        }
    }

    /**
     * 添加索引
     */
    @Test
    public void testIndex()throws Exception{
        IndexResponse response =client.prepareIndex("twitter", "tweet", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引
     */
    @Test
    public void testIndex2()throws Exception{
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        IndexResponse response =client.prepareIndex("weibo", "tweet")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引
     */
    @Test
    public void testIndex3()throws Exception{
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");

        IndexResponse response =client.prepareIndex("qq", "tweet")
                .setSource(json)
                .get();
        System.out.println("索引名称："+response.getIndex());
        System.out.println("类型："+response.getType());
        System.out.println("文档ID："+response.getId()); // 第一次使用是1
        System.out.println("当前实例状态："+response.status());
    }

    /**
     * 添加索引
     */
    @Test
    public void testIndex4()throws Exception{
//        JsonObject jsonObject=new JsonObject();
//
//        jsonObject.addProperty("user", "kimchy");
//        jsonObject.addProperty("postDate", "1989-11-11");
//        jsonObject.addProperty("message", "trying out Elasticsearch");
//
//        IndexResponse response =client.prepareIndex("qq", "tweet")
//                .setSource(jsonObject.toString(),XContentType.JSON)
//                .get();
//        System.out.println("索引名称："+response.getIndex());
//        System.out.println("类型："+response.getType());
//        System.out.println("文档ID："+response.getId()); // 第一次使用是1
//        System.out.println("当前实例状态："+response.status());
    }
}
