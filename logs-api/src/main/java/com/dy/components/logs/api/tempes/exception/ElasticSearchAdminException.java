package com.dy.components.logs.api.tempes.exception;

/**
 * @author : fufeijian
 * @date : 2018/7/21
 * @description
 */
public class ElasticSearchAdminException extends  RuntimeException{

    public ElasticSearchAdminException(String message) {
        super(message);
    }

    public ElasticSearchAdminException(Throwable cause) {
        super(cause);
    }

    public ElasticSearchAdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
