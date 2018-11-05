package com.dy.components.logs.api.log;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * @author fufeijian
 */
public abstract class LogerBuilder {

    XContentBuilder builder;
    String type;
    String indexVersion;
    long id;


    public LogerBuilder(XContentBuilder builder, String type, long id,String indexVersion){
        this.indexVersion=indexVersion;
        this.builder = builder;
        this.type = type;
        this.id = id;
    }

    public XContentBuilder getBuilder() {
        return builder;
    }



    public String getType() {
        return type;
    }



    public long getId() {
        return id;
    }




    /**
     * 在builder基础上构建
     * @return
     */
    public abstract XContentBuilder builder();

}
