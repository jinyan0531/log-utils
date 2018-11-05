package com.dy.components.logs.utils;

public class ObjectCastUtils {
    public static String castToString(Object object){
        if(object!=null) return (String)object;
        else return null;
    }



    public static Long castToLong(Object object){
        if(object!=null) return (Long)object;
        else return null;
    }
}
