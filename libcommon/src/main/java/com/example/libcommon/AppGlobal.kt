package com.example.libcommon

import android.app.Application

object AppGlobal {

    val app by lazy {
        Class.forName("android.app.ActivityThread")
                .getMethod("currentApplication")
                .invoke(null) as Application
    }

}