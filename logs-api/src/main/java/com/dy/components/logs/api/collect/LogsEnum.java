package com.dy.components.logs.api.collect;

/**
 * 日志类别分为四类
 * ERROR  未捕获异常信息
 * HUMAN log.人为打印的信息  人为信息分debug info trace error 等
 * PERFORMS  性能监控
 * EVENT 普通事件跟性能监控日志区别详细程度上面
 * @author fufeijian
 */
public enum LogsEnum {
    //异常信息
    ERROR,
    //人为日志
    HUMAN,
    //性能
    PERFORMS,
    //事件
    EVENT
}
