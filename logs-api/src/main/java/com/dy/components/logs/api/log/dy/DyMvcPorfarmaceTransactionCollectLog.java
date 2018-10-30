package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DyMvcPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog {
    private static final long serialVersionUID = 3966511838728130280L;

    public DyMvcPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }

    String url;

    String sysId;

    String userId;


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
    public String getSysId() {
        return sysId;
    }

    @Override
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }


    @Override
    public String toString() {
        return "DyMvcPorfarmaceTransactionCollectLog{" +
                "url='" + url + '\'' +
                ", sysId='" + sysId + '\'' +
                ", userId='" + userId + '\'' +
                ", version='" + version + '\'' +
                ", sysId='" + sysId + '\'' +
                ", durationTime=" + durationTime +
                ", endTime=" + endTime +
                ", version='" + getVersion() + '\'' +
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
                    getBuilder().startObject("sysId");
                    {
                        getBuilder().field("type", "keyword");
                    }
                    getBuilder().endObject();
                    getBuilder().startObject("endTime");
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
