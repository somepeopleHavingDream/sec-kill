package org.yangxin.seckill.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Md5工具类
 *
 * @author yangxin
 * 2023/3/11 0:59
 */
public class Md5Util {

    /**
     * 盐
     */
    private static final String SALT = "1a2b3c4d";

    /**
     * md5
     *
     * @param src 原串
     * @return md5之后的串
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 用户输入的密码->表单里的密码
     *
     * @param inputPass 用户输入的密码
     * @return 表单里的密码
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 表单里的密码->数据库里的密码
     *
     * @param formPass 表单里的密码
     * @param salt 盐
     * @return 数据库里的密码
     */
    public static String formPassToDbPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 表单里的密码->数据库里的密码
     *
     * @param inputPass 用户输入的密码
     * @param saltDb 数据库盐
     * @return 数据库里的密码
     */
    public static String inputPassToDbPass(String inputPass, String saltDb) {
        return formPassToDbPass(inputPassToFormPass(inputPass), saltDb);
    }

    public static void main(String[] args) {
//        System.out.println(inputPassToFormPass("123456"));
//        System.out.println(formPassToDbPass(inputPassToFormPass("123456"), "1a2b3c4d"));

        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}