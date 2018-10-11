package com.dy.components.logs.api.communication;

/**
 * 网络连接注册
 * @author fufeijian
 */
public interface IRegediter {

    /**
     * 初始化需要传入 host port
     * @param host
     * @param port
     */

    void doRegedit(String host, int port,String user,String password);
    /**
     * @param host
     * @param port
     * @param group
     * @param serviceProviderName
     * @param version
     * @return
     */
    void doRegedit(String host, int port,String user,String password, String group, String serviceProviderName, String version);

    /**
     * 创建Channel
     * @return
     */
    AbstractChannel getChannel();


    /**
     * 是否进行同步连接
     *
     */
    public void connect();
    /**
     * 是否进行重连
     * @param reconnect
     */
     void setReconnect(boolean reconnect);



    public RegisterMeta getRegisterMeta();
}