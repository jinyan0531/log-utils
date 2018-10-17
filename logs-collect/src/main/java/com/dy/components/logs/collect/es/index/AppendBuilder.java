package com.dy.components.logs.collect.es.index;

import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * @author fufeijian
 */
public abstract class AppendBuilder {

    XContentBuilder builder;

    public AppendBuilder(XContentBuilder builder){
        this.builder = builder;
    }

    /**
     * 在builder基础上构建
     * @return
     */
    public abstract XContentBuilder builder();

}
