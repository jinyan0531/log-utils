package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.DefaultTransactionCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class DefaulPorfarmaceTransactionCollectLog extends DefaultTransactionCollectLog {
    private static final long serialVersionUID = 3207941614446848040L;


    long durationTime;

    long endTime;

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "DefaulPorfarmaceTransactionCollectLog{" +
                "durationTime=" + durationTime +
                ", endTime=" + endTime +
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

                    getBuilder().startObject("durationTime");
                    {
                        getBuilder().field("type", "long");
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
