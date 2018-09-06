package com.frank.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Frank on 2018/9/6.
 *
 * @fuction IOC的View属性注解类
 * https://blog.csdn.net/z240336124/article/details/54864333
 * https://www.cnblogs.com/xdp-gacl/p/3622275.html
 */
// Target:目标 Annotation（注解）的位置 ElementType.FIELD:属性 ElementType.METHOD:方法
// ElementType.TYPE:类  ElementType.CONSTRUCTOR：构造函数
@Target(ElementType.FIELD)
//Retention：什么时候生效  RetentionPolicy.CLASS：编译时  RetentionPolicy.RUNTIME：运行时
//RetentionPolicy.SOURCE：源码，资源
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {

    //代表可以传值int类型-->@ViewById(R.id.xxxx)
    int value();
}
