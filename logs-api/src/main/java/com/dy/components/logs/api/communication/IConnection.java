package com.dy.components.logs.api.communication;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author fufeijian
 */
public interface IConnection {
    InetAddress getAddress();

    /**
     * 获取端口
     * @return
     */
    int getPort();

    /**
     * 获取当前连接多少channel
     * @return
     */
    int getChannelMax();

    /**
     * 长连接
     * @return
     */
    int getHeartbeat();

    /**
     * 创建一个channnel
     * @return
     * @throws IOException
     */
    IChannel createChannel() throws IOException;


    IChannel createChannel(int channelNumber) throws IOException;


    void close() throws IOException;
}
