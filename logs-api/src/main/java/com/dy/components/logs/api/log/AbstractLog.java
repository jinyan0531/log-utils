package com.dy.components.logs.api.log;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;

public abstract class AbstractLog implements ILog{
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
