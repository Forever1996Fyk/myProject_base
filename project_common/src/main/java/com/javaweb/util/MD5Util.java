package com.javaweb.util;/**
 * Created by YuKai Fan on 2019/5/3.
 */

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @program: project_parent
 * @description: md5工具类, 加盐
 * @author: YuKai Fan
 * @create: 2019-05-03 22:22
 **/
public class MD5Util {

    /**
     * 加盐md5
     * @param password
     * @return
     */
    public static String generate(String password, String salt) {
        //对原密码加盐后，在进行md5加密
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }
    /**
     * 校验加盐后是否和原文一致
     * @param password
     * @param md5
     * @return
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }

    /**
     * 生成16位随机盐
     * @return
     */
    public static String createSalt() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     * @param str
     * @return
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String text = "yukaifan";
        System.out.println("原始密码：" + text);

        //加盐md5后的值
        String generate = generate(text, createSalt());
        System.out.println("加盐后md5：" + generate);
        System.out.println("是否是同一字符串:" + verify(text, generate));
    }
}
