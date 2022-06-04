package org.yangxin.seckill.redis;

/**
 * @author yangxin
 * 2022/6/4 23:28
 */
@SuppressWarnings("unused")
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
