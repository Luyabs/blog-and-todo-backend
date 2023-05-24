package com.example.blogandtodo.common;

import java.lang.annotation.*;

/**
 * 自定义注解@NeedToken
 * 在使用UserInfo.get()前一定要使用@NeedToken
 */
@Deprecated(since = "本项目默认除登录的所有功能都需要token, 因此不需要本注解")
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedToken {
    boolean required() default true;
}
