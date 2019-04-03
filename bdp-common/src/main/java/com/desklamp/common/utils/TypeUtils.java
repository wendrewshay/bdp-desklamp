/**
 * 类型转换工具类
 */
package com.desklamp.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 类型转换工具类
 * @author Erin
 * @date 2019-03-27 16:45
 */
@Slf4j
public class TypeUtils {

    /**
     * toString
     * @author Erin
     * @date 2019-03-27 16:45
     * @param s
     * @param defaultValue
     * @return java.lang.String
     */
    public static String toString(Object s, String defaultValue) {
        if (s == null || StringUtils.isEmpty(s.toString())) {
            return defaultValue;
        }
        if (s.getClass().isArray()) {
            String str = "";
            Object[] arr = (Object[]) s;
            for (int i = 0; i < arr.length; i++) {
                str += arr[i];
                if (i < arr.length - 1) {
                    str += ",";
                }
            }
            return str;
        }
        return s.toString();
    }

    /**
     * toString
     * @author Erin
     * @date 2019-03-27 16:45
     * @param s
     * @return java.lang.String
     */
    public static String toString(Object s) {
        return toString(s, "");
    }

    /**
     * toString
     * @author Erin
     * @date 2019-03-27 16:45
     * @param arr 字符串数组
     * @return java.lang.String
     */
    public static String toString(String[] arr) {
        return toString(arr, ",");
    }

    /**
     * toString
     * @author Erin
     * @date 2019-03-27 16:45
     * @param arr
     * @param sep
     * @return java.lang.String
     */
    public static String toString(String[] arr, String sep) {
        if (arr == null) {
            return "";
        }
        String s = "";
        for (int i = 0; i < arr.length; i++) {
            s += arr[i] + (i < arr.length - 1 ? sep : "");
        }
        return s;
    }

    /**
     * toLong
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @param defaultValue
     * @return java.lang.Long
     */
    public static Long toLong(Object value, Long defaultValue) {
        try {
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return Long.valueOf(toString(value));
        } catch (Exception e) {
            log.error("类型转换错误，自动返回默认值：{0}", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * toLong
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return java.lang.Long
     */
    public static Long toLong(Object value) {
        return toLong(value, null);
    }

    /**
     * toDouble
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @param defaultValue
     * @return java.lang.Double
     */
    public static Double toDouble(Object value, Double defaultValue) {
        try {
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return Double.valueOf(toString(value));
        } catch (Exception e) {
            log.error("类型转换错误，自动返回默认值：{0}", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * toDouble
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return java.lang.Double
     */
    public static Double toDouble(Object value) {
        return toDouble(value, null);
    }

    /**
     * toInteger
     * @param value
     * @param defaultValue
     * @return java.lang.Integer
     */
    public static Integer toInteger(Object value, Integer defaultValue) {
        try {
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return Integer.valueOf(toString(value));
        } catch (Exception e) {
            log.error("类型转换错误，自动返回默认值：{0}", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * toInteger
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return java.lang.Integer
     */
    public static Integer toInteger(Object value) {
        return toInteger(value, null);
    }

    /**
     * toFloat
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @param defaultValue
     * @return java.lang.Float
     */
    public static Float toFloat(Object value, Float defaultValue) {
        try {
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return Float.valueOf(toString(value));
        } catch (Exception e) {
            log.error("类型转换错误，自动返回默认值：{0}", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * toFloat
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return java.lang.Float
     */
    public static Float toFloat(Object value) {
        return toFloat(value, null);
    }

    /**
     * toInt
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @param defaultValue
     * @return int
     */
    public static int toInt(Object value, int defaultValue) {
        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        } else {
            return toInteger(value + "", defaultValue);
        }
    }

    /**
     * toInt
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return int
     */
    public static int toInt(Object value) {
        return toInt(value, 0);
    }

    /**
     * toShort
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @param defaultValue
     * @return java.lang.Short
     */
    public static Short toShort(Object value, Short defaultValue) {
        try {
            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }
            return Short.valueOf(toString(value, "").split("\\.")[0]);
        } catch (Exception e) {
            log.error("类型转换错误，自动返回默认值：{0}", defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * toShort
     * @author Erin
     * @date 2019-03-27 16:45
     * @param value
     * @return java.lang.Short
     */
    public static Short toShort(Object value) {
        return toShort(value, null);
    }
}
