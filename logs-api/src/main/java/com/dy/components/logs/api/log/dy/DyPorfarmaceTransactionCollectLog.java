package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DyPorfarmaceTransactionCollectLog extends DefaulPorfarmaceTransactionCollectLog {
    private static final long serialVersionUID = 4713932672146332392L;

    String version;


    public String getVersion() {
        return version;
    }

    String logType = "DyMvcPorfarmaceTransactionCollectLog";

    @Override
    public String getLogType() {
        return logType;
    }

    @Override
    public void setLogType(String logType) {
        this.logType = logType;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "DyPorfarmaceTransactionCollectLog{" +
                "version='" + version + '\'' +
                ", logType='" + logType + '\'' +
                ", durationTime=" + durationTime +
                ", endTime=" + endTime +
                ", durationTime=" + getDurationTime() +
                ", endTime=" + getEndTime() +
                ", parames='" + getParames() + '\'' +
                ", name='" + getName() + '\'' +
                ", startTime=" + getStartTime() +
                ", childrenLogs=" + getChildrenLogs() +
                ", logId=" + getLogId() +
                ", parentLogId=" + getParentLogId() +
                ", firstLogId=" + getFirstLogId() +
                ", message='" + getMessage() + '\'' +
                ", completed=" + isCompleted() +
                ", end=" + isEnd() +
                ", first=" + isFirst() +
                ", messageTempletId=" + getMessageTempletId() +
                '}';
    }

    public  LogerBuilder toXContentBuilder(XContentBuilder builder) {


        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {

                    getBuilder().startObject("version");
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
