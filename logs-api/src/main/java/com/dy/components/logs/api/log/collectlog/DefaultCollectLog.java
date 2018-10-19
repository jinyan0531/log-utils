package com.dy.components.logs.api.log.collectlog;

import com.dy.components.logs.api.log.AbstractLog;
import com.dy.components.logs.api.log.ILog;
import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * 采集日志父类
 * @author fufeijian newone1997@gmail.com
 */
public class DefaultCollectLog extends AbstractLog implements ILog {
    private static final long serialVersionUID = -4276291783328905418L;
    /**
     * 唯一ID
     */
    LogId logId;

    /**
     * 父类的Logid
     */
    LogId parentLogId;

    /**
     * 第一个Logid
     */
    LogId firstLogId;




    /**
     * 内容
     */
    String message;


    /**
     * 内容Id 相同
     */
    long messageTempletId;

    /**
     * 线程是否已经完成
     */
    boolean isCompleted;

    /**
     * 最后一个标记，进程完成
     */
    boolean isEnd;


    boolean isFirst;

    /**
     * 类型
     */
    String logType = "DefaultCollectLog";


    public DefaultCollectLog(String message,long messageTempletId,LogId firstLogId,LogId parentLogId,LogId logId){
        this.message = message;
        this.firstLogId = firstLogId;
        this.parentLogId = parentLogId;
        this.logId = logId;
        this.isCompleted = false;
        this.messageTempletId = messageTempletId;
    }


    public DefaultCollectLog(){}



    public void end(){
        this.isCompleted =true;
    }

    public LogId getLogId() {
        return logId;
    }

    public void setLogId(LogId logId) {
        this.logId = logId;
    }

    public LogId getParentLogId() {
        return parentLogId;
    }

    public void setParentLogId(LogId parentLogId) {
        this.parentLogId = parentLogId;
    }

    public LogId getFirstLogId() {
        return firstLogId;
    }

    public void setFirstLogId(LogId firstLogId) {
        this.firstLogId = firstLogId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public long getMessageTempletId() {
        return messageTempletId;
    }

    public void setMessageTempletId(long messageTempletId) {
        this.messageTempletId = messageTempletId;
    }

    @Override
    public String toString() {
        return "DefaultCollectLog{" +
                "logId=" + logId +
                ", parentLogId=" + parentLogId +
                ", firstLogId=" + firstLogId +
                ", message='" + message + '\'' +
                ", messageTempletId=" + messageTempletId +
                ", isCompleted=" + isCompleted +
                ", isEnd=" + isEnd +
                ", isFirst=" + isFirst +
                ", logType='" + logType + '\'' +
                '}';
    }


    public  XContentBuilder getXConBuilder(){
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();

            LogerBuilder appenduilder = toXContentBuilder(builder);
            builder.startObject();
            {

                builder.startObject(appenduilder.getType());
                {
                    builder.startObject("properties");
                    {

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

    @Override
    public LogerBuilder toXContentBuilder(XContentBuilder builder) {
        LogerBuilder logerBuilder = new LogerBuilder(builder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {

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
                    return builder;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return logerBuilder;
    }


    private   void LoginIdBuild(XContentBuilder builder) throws IOException {

        //builder.field("type", "nested");
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
    }
}
