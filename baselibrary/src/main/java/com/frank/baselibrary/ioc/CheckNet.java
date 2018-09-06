package com.frank.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Frank on 2018/9/6.
 *
 * @fuction IOC检查网络注解类
 */
// Target:目标 Annotation（注解）的位置 ElementType.FIELD:属性 ElementType.METHOD:方法
// ElementType.TYPE:类  ElementType.CONSTRUCTOR：构造函数
@Target(ElementType.METHOD)
//Retention：什么时候生效  RetentionPolicy.CLASS：编译时  RetentionPolicy.RUNTIME：运行时
//RetentionPolicy.SOURCE：源码，资源
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {

}
