package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class DySqlPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog{
    private static final long serialVersionUID = 8432490857419296019L;


    String sql;

    String userId;
    String logType = "DySqlPorfarmaceTransactionCollectLog";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "DySqlPorfarmaceTransactionCollectLog{" +
                "sql='" + sql + '\'' +
                ", userId='" + userId + '\'' +
                ", version='" + version + '\'' +
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
                '}';
    }

    public  LogerBuilder toXContentBuilder(XContentBuilder builder) {


        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();


        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {
                    getBuilder().startObject("sysId");
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
