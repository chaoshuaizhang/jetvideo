package com.example.libcommon.util.ext

import android.util.Log

fun Any.logd(msg: String) {
    Log.d(this::class.simpleName, msg)
}

fun Any.loge(msg: String) {
    Log.e(this::class.simpleName, msg)
}