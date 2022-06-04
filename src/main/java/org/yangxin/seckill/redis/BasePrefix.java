package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2022/6/4 23:02
 */
@SuppressWarnings("unused")
public abstract class BasePrefix implements KeyPrefix {

    @Override
    public int expireSeconds() {
        return 0;
    }

    @Override
    public String getPrefix() {
        return null;
    }
}
