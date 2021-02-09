package com.example.jetvideo.util

import com.example.libcommon.AppGlobal

object ScreenUtil {

    val w by lazy {
        val metrics = AppGlobal.app.resources.displayMetrics
        metrics.widthPixels
    }

    val h by lazy {
        val metrics = AppGlobal.app.resources.displayMetrics
        metrics.widthPixels
    }

}