package com.dy.components.logs.api.log.collectlog;

import com.dy.components.logs.api.log.ILog;
import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.List;

public class DefaultTransactionCollectLog extends  DefaultCollectLog {

    private static final long serialVersionUID = 6814636162611128034L;




    String parames;

    String name;


    long startTime;

    List<ILog> childrenLogs;

    public DefaultTransactionCollectLog() {
    }



    public String getParames() {
        return parames;
    }

    public void setParames(String parames) {
        this.parames = parames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public List<ILog> getChildrenLogs() {
        return childrenLogs;
    }

    public void setChildrenLogs(List<ILog> childrenLogs) {
        this.childrenLogs = childrenLogs;
    }

    @Override
    public String toString() {
        return "DefaultTransactionCollectLog{" +
                "parames='" + parames + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", childrenLogs=" + childrenLogs +
                ", logId=" + logId +
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

//    public XContentBuilder toXContentBuilder()  {
//        XContentBuilder builder = null;
//        try {
//            builder = XContentFactory.jsonBuilder();
//
//            builder = DefaultBuilder.defaultLogMapping(builder,toXContentBuilder(builder));
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return builder;
//
//    }

    public  LogerBuilder toXContentBuilder(XContentBuilder builder){

        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID,getIndexVersion()) {
            @Override
            public XContentBuilder builder() {

                try {

                    getBuilder().startObject("parames");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();
                    getBuilder().startObject("name");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();

                    getBuilder().startObject("startTime");
                    {
                        getBuilder().field("type", "long");
                    }
                    getBuilder().endObject();

//                    if(childrenLogs!=null) {
//
//                        int length = childrenLogs.size();
//                        if(length>0) {
//                            getBuilder().startObject("childrenLogs");
//                            {
//                                getBuilder().field("type", "nested");
//                                getBuilder().startObject("properties");
//                                {
//                                    for (int i = 0; i < length; i++) {
//                                        childrenLogs.get(i).toXContentBuilder(getBuilder());
//                                    }
//                                }
//                                getBuilder().endObject();
//                            }
//                            getBuilder().endObject();
//                        }
//                    }
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
