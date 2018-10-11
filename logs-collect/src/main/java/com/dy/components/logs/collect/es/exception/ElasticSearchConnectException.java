package com.dy.components.logs.collect.es.exception;

/**
 * @author : fufeijian
 * @date : 2018/7/21
 * @description
 */
public class ElasticSearchConnectException extends ElasticSearchException{

    public ElasticSearchConnectException(String message) {
        super(message);
    }

    public ElasticSearchConnectException(Throwable cause) {
        super(cause);
    }

    public ElasticSearchConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
