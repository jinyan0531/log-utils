package com.dy.components.logs.api.communication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 通道
 */
public interface IChannel {
    /**
     * 获取通道总数
     * @return
     */
    int getChannelNumber();

    /**
     * 发送消息后返回后的处理
     * @param listener
     */
    void addReturnListener(IReturnListener listener);

    /**
     * 移除返回监听
     * @param listener
     */
    void removeReturnListener(IReturnListener listener);

    /**
     * 移除返回处理
     */
    void clearReturnListeners();


    /**
     * 增加发送确认提醒
     * @param listener
     */
    void addConfirmListener(IConfirmListener listener);

    /**
     * 移除确认
     * @param listener
     * @return
     */
    boolean removeConfirmListener(IConfirmListener listener);

    /**
     * 清楚所有确认
     */
    void clearConfirmListeners();

    /**
     * 关闭通道
     * @throws IOException
     * @throws TimeoutException
     */
    void close() throws IOException, TimeoutException;

    /**
     * 往通道里面写内容
     * @param body
     * @param channelKey
     */
    void write(String channelKey,byte[] body);


}
