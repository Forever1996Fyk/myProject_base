package com.javaweb.util;/**
 * Created by 恺b on 2019/5/3.
 */

/**
 * @program: project_parent
 * @description: 字符串工具类
 * @author: YuKai Fan
 * @create: 2019-05-03 22:44
 **/
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.trim().length() == 0;
    }
}
