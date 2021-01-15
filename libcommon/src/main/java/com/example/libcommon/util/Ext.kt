package com.example.libcommon.util

import android.animation.Animator
import android.animation.TimeInterpolator

fun Any.TAG(): String {
    return javaClass.simpleName
}

inline fun Any.measureTime(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    val end = System.currentTimeMillis()
    return end - start
}
