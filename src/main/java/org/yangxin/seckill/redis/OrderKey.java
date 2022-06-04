package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2022/6/4 23:28
 */
@SuppressWarnings("unused")
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
