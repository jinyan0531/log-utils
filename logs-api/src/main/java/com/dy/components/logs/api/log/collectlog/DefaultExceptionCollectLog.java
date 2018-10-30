package com.dy.components.logs.api.log.collectlog;

import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author fufeijian newone1997@gmail.com
 */
public class DefaultExceptionCollectLog extends  DefaultCollectLog{
    private static final long serialVersionUID = 569930694337990552L;



    /**
     * 方法参数
     */
    String params;

    /**
     * 异常堆栈信息
     */
    String stacks;

    /**
     * 日志时间
     */
    long startTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStacks() {
        return stacks;
    }

    public void setStacks(String stacks) {
        this.stacks = stacks;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "DefaultExceptionCollectLog{" +
                "params='" + params + '\'' +
                ", stacks='" + stacks + '\'' +
                ", startTime=" + startTime +
                ", logId=" + logId +
                ", parentLogId=" + parentLogId +
                ", firstLogId=" + firstLogId +
                ", message='" + message + '\'' +
                ", messageTempletId=" + messageTempletId +
                ", isCompleted=" + isCompleted +
                ", isEnd=" + isEnd +
                ", isFirst=" + isFirst +
                ", logType='" + logType + '\'' +
                ", logId=" + getLogId() +
                ", parentLogId=" + getParentLogId() +
                ", firstLogId=" + getFirstLogId() +
                ", message='" + getMessage() + '\'' +
                ", completed=" + isCompleted() +
                ", logType='" + getLogType() + '\'' +
                ", end=" + isEnd() +
                ", first=" + isFirst() +
                ", messageTempletId=" + getMessageTempletId() +
                '}';
    }

    public LogerBuilder toXContentBuilder(XContentBuilder builder){

        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {

                    getBuilder().startObject("parames");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();


                    getBuilder().startObject("stacks");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();



                    getBuilder().startObject("startTime");
                    {
                        getBuilder().field("type", "long");
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
