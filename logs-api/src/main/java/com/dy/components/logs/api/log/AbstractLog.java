package com.dy.components.logs.api.log;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractLog implements Serializable, ILog{
    private static final long serialVersionUID = -1228223602458834533L;

    @Override
    public byte[] logSerializa(DefaultCollectLog log) {
        return null;
    }

    @Override
    public DefaultCollectLog assembleLogs(DefaultCollectLog parent, DefaultCollectLog child) {
        return null;
    }

    @Override
    public DefaultCollectLog readLogsFromBytes(byte[] bytes) {
        return null;
    }



}
