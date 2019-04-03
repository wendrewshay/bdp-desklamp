package com.desklamp.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射对对象的属性、方法进行操作
 * @author jw
 * @date 2019/3/22
 */
@Slf4j
public class ReflectionUtils {

    /**
     * 获取某个类的所有的属性,包括修饰符和数据类型
     * @author jw
     * @date 2019/3/22 10:59
     * @param clazz:类的class
     * @return java.util.List<java.lang.StringBuilder>
     */
    public static List<StringBuilder> getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int len = fields.length;
        List<StringBuilder> list = new ArrayList<>();
        StringBuilder sb;
        for (int i = 0; i < len; i++) {
            Field field = fields[i];
            sb = new StringBuilder();
            // 修饰符
            String modifier = Modifier.toString(field.getModifiers());
            sb.append(modifier + " ");
            // 数据类型
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();
            sb.append(typeName + " ");
            // 属性名
            String fieldName = field.getName();
            sb.append(fieldName + ";");
            list.add(sb);
        }
        return list;
    }

    /**
     * 获取某个类的所有的属性名
     * @author jw
     * @date 2019/3/25 11:41
     * @param clazz:类的class
     * @return java.util.List<java.lang.Object>
     */
    private static List<Object> getField(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        List<Object> list = new ArrayList<>();
        for(Field f : fields){
            list.add(f.getName());
        }
        return list;
    }

    /**
     * 反射调用对象的方法
     * @author jw
     * @date 2019/3/22 11:03
     * @param clazz:对象
     * @param methodName:方法名称
     * @param paramTypes:参数类型
     * @param params:参数值
     * @return java.lang.Object
     */
    public static Object readObjMethod(Class<?> clazz,String methodName,Class<?>[] paramTypes,Object[] params) throws  Exception{
        Class<?> cls = clazz;
        Method method = cls.getDeclaredMethod(methodName, paramTypes);
        /**
         * 可以访问private修饰的方法
         */
        method.setAccessible(true);
        Object val = method.invoke(cls.newInstance(), params);
        return val;
    }

    /**
     * 获取某个类的实例
     * @author jw
     * @date 2019/3/22 14:09
     * @param clazz:类的class
     * @return java.lang.Object
     */
    public static Object getInstance(Class<?> clazz) throws Exception{
        return clazz.newInstance();
    }

    /**
     * 给对象的属性赋值
     * @author jw
     * @date 2019/3/22 14:43
     * @param fieldName:属性名
     * @param object:实体类对象
     * @param value:新值
     */
    public static void setFieldValueByFieldName(String fieldName, Object object,String value) throws Exception{
        Class<?> clazz = object.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object,value);
//        System.out.println(clazz.getDeclaredField(fieldName).get(object));
    }

    /**
     * 获取对象的属性值
     * @author jw
     * @date 2019/3/22 14:42
     * @param fieldName:属性名称
     * @param object:对象
     * @return java.lang.String
     */
    public static String getFieldValueByFieldName(String fieldName, Object object){
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String)field.get(object);
        } catch (Exception e) {
            log.error("获取属性值报错,{}",e.getMessage(),e);
            return null;
        }
    }

    public static void main(String[] args) {
//        try {
//            Object object = ReflectionUtils.readObjMethod(TestMethod.class,"getStr",new Class[]{String.class},new Object[]{"ddd"});
//            System.out.println(object);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        TestMethod testMethod = (TestMethod) ReflectionUtils.getInstance(TestMethod.class);
//        System.out.println(testMethod.getStr("ddddd"));

//        setFieldValueByFieldName("name",new TestConsts(),"ccccc");
//        List<Object> field = getField(TableData.class);
//        field.forEach(name -> {
//            System.out.println(name);
//        });
    }
}
