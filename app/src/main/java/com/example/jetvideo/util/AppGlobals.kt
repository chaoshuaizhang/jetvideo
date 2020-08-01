package com.example.jetvideo.util

import android.app.Application
import kotlin.reflect.KClass

class AppGlobals {
    companion object {
        lateinit var app: Application

        fun getAppInstance(): Application {
            val aClass = Class.forName("android.app.ActivityThread")
            var method = aClass.getDeclaredMethod("currentApplication")
            var app: Application = method.invoke(null, null) as Application
            return app;
        }
    }
}