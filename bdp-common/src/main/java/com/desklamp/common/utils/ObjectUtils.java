package com.desklamp.common.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 基于commons-beanutils的实体类/Map/List转换工具类
 *
 * @author Erin
 * @create 2019-03-25 10:24
 */
public class ObjectUtils<T> {

    /**
     * Map转javaBean（map的key要与javaBean一致）
     *
     * @param map
     * @param t 目标实体类型
     * @return T 转换后的实体类，与参数T一致
     * @throws Exception
     */
    public static <T> T map2Obj(Map map, T t) throws Exception {
        BeanUtils.copyProperties(t, map);
        return t;
    }

    /**
     * javaBean转Map
     * @param obj 实体类
     * @return java.util.Map
     * @throws Exception
     */
    public static Map obj2Map(Object obj) throws Exception {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = obj.getClass().getDeclaredField(fields[i].getName());
            f.setAccessible(true);
            Object o = f.get(obj);
            map.put(fields[i].getName(), o);
        }
        return map;
    }

    /**
     * javaBean转List
     * @param obj 实体类
     * @return java.util.List
     * @throws Exception
     */
    public static List obj2List(Object obj) throws Exception {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = obj.getClass().getDeclaredField(fields[i].getName());
            f.setAccessible(true);
            list.add(f.get(obj).toString());
        }
        return list;
    }

    /**
     * 字符串转List
     * @param str 字符串举例"[1,2,3]"
     * @return
     */
    public static List str2List(String str) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        return Arrays.asList(str.substring(1,str.length()-1));
    }
}
