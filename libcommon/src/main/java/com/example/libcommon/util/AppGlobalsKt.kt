package com.example.libcommon.util

import android.app.Application

class AppGlobalsKt {
    companion object {
        lateinit var app: Application

        fun getAppInstance(): Application {
            val aClass = Class.forName("android.app.ActivityThread")
            var method = aClass.getDeclaredMethod("currentApplication")
            return method.invoke(null) as Application;
        }
    }

}