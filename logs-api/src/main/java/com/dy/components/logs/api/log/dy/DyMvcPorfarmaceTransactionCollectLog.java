package com.dy.components.logs.api.log.dy;

import com.dy.components.logs.api.log.ILog;
import com.dy.components.logs.api.log.LogerBuilder;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.log.collectlog.DefaultTransactionCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DyMvcPorfarmaceTransactionCollectLog extends  DyPorfarmaceTransactionCollectLog {
    private static final long serialVersionUID = 3966511838728130280L;



    String url;

    String userId;

    String uri;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void addChildCollectLog(ILog log){
        List<ILog> list = getChildrenLogs();
        list.add(log);
        setChildrenLogs(list);
    }


    @Override
    public String toString() {
        return "DyMvcPorfarmaceTransactionCollectLog{" +
                "url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                ", uri='" + uri + '\'' +
                ", version='" + version + '\'' +
                ", durationTime=" + durationTime +
                ", findUncompleted=" + findUncompleted() +
                ", version='" + getVersion() + '\'' +
                ", logType='" + logType + '\'' +
                ", endTime=" + endTime +
                ", logType='" + getLogType() + '\'' +
                ", durationTime=" + getDurationTime() +
                ", endTime=" + getEndTime() +
                ", parames='" + getParames() + '\'' +
                ", name='" + getName() + '\'' +
                ", startTime=" + getStartTime() +
                ", childrenLogs=" + getChildrenLogs() +
                ", findUncompleted=" + findUncompleted() +
                ", indexVersion='" + getIndexVersion() + '\'' +
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


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public DyMvcPorfarmaceTransactionCollectLog findUncompleted(){
        DyMvcPorfarmaceTransactionCollectLog subEntry = null;
        if(getChildrenLogs()==null){
            setChildrenLogs(new ArrayList<ILog>());
        }
        if (!getChildrenLogs().isEmpty()) {
            subEntry = (DyMvcPorfarmaceTransactionCollectLog) getChildrenLogs().get(getChildrenLogs().size() - 1);
            if (subEntry.isCompleted()) {
                subEntry = null;
            }
        }
        return subEntry;
    }
    public LogerBuilder toXContentBuilder(XContentBuilder builder) {


        XContentBuilder supperBuilder  = super.toXContentBuilder(builder).builder();

        LogerBuilder logerBuilder = new LogerBuilder(supperBuilder,this.getClass().getSimpleName(),serialVersionUID,this.version) {
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

                    getBuilder().startObject("uri");
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
