package com.example.libcommon.util;

import android.app.Application;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class AppGlobal {

    private static Application app;

    public static Application getAppInstance() {
        new Executor(){
            @Override
            public void execute(Runnable command) {

            }
        };
        if (app == null) {
            try {

                Class<?> aClass = Class.forName("android.app.ActivityThread");
                Method method = aClass.getDeclaredMethod("currentApplication");
                app = (Application) method.invoke(null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return app;
    }

}
