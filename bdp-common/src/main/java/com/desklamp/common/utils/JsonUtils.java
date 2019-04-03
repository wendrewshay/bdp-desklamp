package com.desklamp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * 基于FastJson的Json工具类
 * 
 * @author Erin
 * @date 2019-03-23 15:15
 */
public class JsonUtils {
	/**
	 * 将对象转换成Json字符串,支持JavaBean、集合和Map类型
	 * @author Erin
	 * @date 2019-03-23 15:15
	 * @param obj
	 * @return <T>
	 */
	public static <T> String object2Json(T obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 将JSON字符串转换成对象
	 * @author Erin
	 * @date 2019-03-23 15:17
	 * @param str 待处理字符串
	 * @param clazz 对象的字节码类型
	 * @return <T> 转换后对象
	 */
	public static <T> T json2Object(String str, Class<T> clazz) {
		return JSON.parseObject(str, clazz);
	}

	/**
	 * 把JSON字符串转换为JSONObject
	 * @author Erin
	 * @date 2019-03-23 15:19
	 * @param str JSON字符串
	 * @return com.alibaba.fastjson.JSONObject
	 */
	public static JSONObject str2JSONObject(String str) {
		return JSON.parseObject(str);
	}

	/**
	 * 把JSON字符串转换成JSONArray
	 * @author Erin
	 * @date 2019-03-23 15:22
	 * @param str JSON字符串
	 * @return com.alibaba.fastjson.JSONArray
	 */
	public static JSONArray str2JSONArray(String str) {
		return JSON.parseArray(str);
	}

	/**
	 * 把JSON文本转换成JavaBean集合
	 * @author Erin
	 * @date 2019-03-23 15:24
	 * @param str JSON文本
	 * @param clazz T字节码类型
	 * @return java.util.List<T>
	 */
	public static <T> List<T> str2List(String str, Class<T> clazz) {
		return JSON.parseArray(str, clazz);
	}

	/**
	 * 将JavaBean转换为JSONObject
	 * @author Erin
	 * @date 2019-03-23 15:27
	 * @param obj JavaBean
	 * @return Object
	 */
	public static <T> Object toJSON(T obj) {
		return JSON.toJSON(obj);
	}

	/**
	 * 对json字符串格式化输出
	 * @author Erin
	 * @date 2019-03-23 15:27
	 * @param jsonStr json字符串
	 * @return java.lang.String
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
				case '{':
				case '[':
					sb.append(current);
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
					break;
				case '}':
				case ']':
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
					sb.append(current);
					break;
				case ',':
					sb.append(current);
					if (last != '\\') {
						sb.append('\n');
						addIndentBlank(sb, indent);
					}
					break;
				default:
					sb.append(current);
			}
		}
		return sb.toString();
	}

	/**
	 * 添加space
	 * @author Erin
	 * @date 2019-03-23 15:30
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
}
