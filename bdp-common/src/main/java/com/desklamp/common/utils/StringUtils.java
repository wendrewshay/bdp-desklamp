package com.desklamp.common.utils;

import java.util.UUID;

/**
 * 字符串处理工具类
 *
 * @author Erin
 * @create 2019-03-23 09:52
 */
public class StringUtils {

    /**
     * 生成32位UUID
     * @author Erin
     * @date 2019-03-23 10:28
     * @return String
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 字符串或对象非空判断
     * @author Erin
     * @date 2019-03-23 10:30
     * @return String
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断字符串不为空
     * @author jw
     * @date 2019/3/26 16:52
     * @param str:字符串
     * @return boolean
     */
    public static boolean isNotEmpty(Object str){
        return !isEmpty(str);
    }

    /**
     * 字符串格式化（空格消除，\r\n消除）
     * @author Erin
     * @date 2019-03-23 10:39
     * @param str 需要格式化的字符串
     * @return java.lang.String
     */
    public static String strFormat(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll("\r|\n","").replaceAll(" ","");
    }

    /**
     * 根据regex剪切str
     * 用于OCR识别剪切图片url
     * @author Erin
     * @date 2019-03-23 20:23
     * @param str 源字符串
     * @param reg 剪切规则
     * @return java.lang.String
     */
    public static String substring(String str, String regex) {
        if (str.contains(regex)) {
            return str.substring(0,str.indexOf(regex));
        }
        return str;
    }
}
