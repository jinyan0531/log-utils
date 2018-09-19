package com.dy.components.logs.api.communication;

import java.io.IOException;

public interface IReturnListener {
    /**
     *
     * @param body
     * @throws IOException
     */
    void handleReturn(byte[] body) throws IOException;

    /**
     * 获取ListenerId,唯一编码
     */
    void getListenerId();
}
