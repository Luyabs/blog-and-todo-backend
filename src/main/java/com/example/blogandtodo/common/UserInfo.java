package com.example.blogandtodo.common;

import com.example.blogandtodo.common.exception.ServiceException;

/**
 * 在解析token后用于存储用户id的ThreadLocal
 * 在使用UserInfo.get()前需要使用自定义注解@NeedToken来标明需要解析token
 */
public class UserInfo {
    private static final ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    public static void set(Integer userId){
        userThreadLocal.set(userId);
    }

    public static Integer get(){
        Integer res = userThreadLocal.get();
        if (res == null)
            throw new ServiceException("UserInfo为空, 请尝试在controller方法前增加@NeedToken自定义注解");
        return res;
    }

}
