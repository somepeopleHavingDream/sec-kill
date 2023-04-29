package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2023/4/29 21:57
 */
public class SecKillKey extends BasePrefix {

    public SecKillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecKillKey isGoodOver = new SecKillKey(0, "go");
}
