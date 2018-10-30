package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DyPorfarmaceTransactionCollectLog extends DefaulPorfarmaceTransactionCollectLog {
    private static final long serialVersionUID = 4713932672146332392L;

    public DyPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }
    String version;

    String sysId;

    public String getVersion() {
        return version;
    }



    public void setVersion(String version) {
        this.version = version;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    @Override
    public String toString() {
        return "DyPorfarmaceTransactionCollectLog{" +
                "sysId='" + sysId + '\'' +
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
                ", logType='" + getLogType() + '\'' +
                ", end=" + isEnd() +
                ", first=" + isFirst() +
                ", messageTempletId=" + getMessageTempletId() +
                ", XConBuilder='" + getXConBuilder() + '\'' +
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

                    getBuilder().startObject("sysId");
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
