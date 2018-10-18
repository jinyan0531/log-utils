package com.dy.components.logs.api.log;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 *
 * @author fufeijian
 */
public interface ILog {
    /**
     * 序列化日志
     * @param log
     * @return
     */
    byte[] logSerializa(DefaultCollectLog log);

    /**
     * 聚合日志
     * @param parent
     * @param child
     * @return
     */
    DefaultCollectLog assembleLogs(DefaultCollectLog parent,DefaultCollectLog child);


    /**
     *
     * @param bytes
     * @return
     */
    DefaultCollectLog readLogsFromBytes(byte[] bytes);

    public LogerBuilder toXContentBuilder(XContentBuilder builder);

}
