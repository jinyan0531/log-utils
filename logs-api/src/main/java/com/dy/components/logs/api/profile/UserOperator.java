package com.dy.components.logs.api.profile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 利用反射方式存储当前用户的一些缓存信息
 *
 * @author fufeijian [newone1997@gmail.com]
 */
public class UserOperator {


    private static Class CIPRuntime = null;

    private static Class CIPRuntimeOperatorClass = null;

    private static Method getOperateSubjectMethod = null;

    //获取用户id
    private static Method getSubject_idMethod = null;
    static {
        try {
            /**
             * 当前用户信息类
             */
            CIPRuntime = Class.forName("com.yd.common.runtime.CIPRuntime");
            /**
             * 用户操作对象
             */
            CIPRuntimeOperatorClass = Class.forName("com.yd.common.runtime.CIPRuntimeOperator");

            //获取用户
            getOperateSubjectMethod =CIPRuntime.getMethod("getOperateSubject");


            getSubject_idMethod = CIPRuntimeOperatorClass.getMethod("getSubject_id",null);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public static String getUserId(){
        //获取当前用户信息
        Object user = getCurrentUser();

        if(user==null) {
            return null;
        }else {
            try {
                return (String) getSubject_idMethod.invoke(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    static Object getCurrentUser(){
        try {
            return getOperateSubjectMethod.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }







}
