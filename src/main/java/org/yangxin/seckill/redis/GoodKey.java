package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2023/4/29 20:39
 */
public class GoodKey extends BasePrefix {

    private GoodKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodKey getSecKillGoodStock = new GoodKey(0, "gs");
}
