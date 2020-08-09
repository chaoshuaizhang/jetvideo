package com.example.libnetwork.util;

import java.lang.reflect.Field;

public class CheckUtil {
    /*
    * 校验是否是基本数据类型
    * */
    public static boolean validBaseJavaType(Object o){
        if(o != null){
            try {
                Field field = o.getClass().getField("TYPE");
                Class clazz = (Class) field.get(null);
                return clazz.isPrimitive();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
