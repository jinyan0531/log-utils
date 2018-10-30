package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DyMvcPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog {
    private static final long serialVersionUID = 3966511838728130280L;



    String url;


    String userId;

    String logType = "DyMvcPorfarmaceTransactionCollectLog";

    @Override
    public String getLogType() {
        return logType;
    }

    @Override
    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "DyMvcPorfarmaceTransactionCollectLog{" +
                "url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                ", version='" + version + '\'' +
                ", durationTime=" + durationTime +
                ", endTime=" + endTime +
                '}';
    }

    public LogerBuilder toXContentBuilder(XContentBuilder builder) {


        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {

                    getBuilder().startObject("url");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();
                    getBuilder().startObject("userId");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();

                    return getBuilder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return logerBuilder;
    }
}
