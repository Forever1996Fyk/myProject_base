package com.javaweb.util;/**
 * Created by YuKai Fan on 2019/5/3.
 */

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @program: project_parent
 * @description: map工具类
 * @author: YuKai Fan
 * @create: 2019-05-03 23:11
 **/
public class MapUtil {

    /**
     * 将map转为实体
     * @param beanClass
     * @param map
     * @return
     */
    public static Object mapToObject(Class<?> beanClass, Map<String, Object> map) throws Exception {
        if (map == null) {
            return null;
        }

        //利用反射获取，实体对象
        Object obj = beanClass.newInstance();
        BeanUtils.populate(obj, map);
        return obj;
    }

    /**
     * 将实体转为map
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }

        return new BeanMap(obj);
    }
}
