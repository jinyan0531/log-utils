package com.dy.components.logs.api.log.collectlog;

import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DefaultEventCollectLog extends  DefaultCollectLog {
    private static final long serialVersionUID = 2691092350851782556L;



    public LogerBuilder toXContentBuilder(XContentBuilder builder){



        return  super.toXContentBuilder(builder);
    }
}
