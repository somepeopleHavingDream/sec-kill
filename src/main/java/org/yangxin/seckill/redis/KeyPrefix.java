package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2022/6/4 23:00
 */
@SuppressWarnings({"unused", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
