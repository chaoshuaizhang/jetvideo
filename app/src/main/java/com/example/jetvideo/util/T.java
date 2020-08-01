package com.example.jetvideo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class T {
    public static void m(){
        Class<?> aClass = null;
        try {
            aClass = Class.forName("android.app.ActivityThread");
            Method getApplica = aClass.getDeclaredMethod("getApplica");
            Object invoke = getApplica.invoke(null, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
