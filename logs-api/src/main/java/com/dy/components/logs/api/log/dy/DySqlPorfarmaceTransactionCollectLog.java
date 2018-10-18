package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DySqlPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog{
    private static final long serialVersionUID = 8432490857419296019L;

    public DySqlPorfarmaceTransactionCollectLog(String message, long messageTempletId, LogId firstLogId, LogId parentLogId, LogId logId) {
        super(message, messageTempletId, firstLogId, parentLogId, logId);
    }
    String sql;

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


    public LogerBuilder toXContentBuilder(XContentBuilder builder) {


        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID) {
            @Override
            public XContentBuilder builder() {

                try {

                    getBuilder().startObject("sql");
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
