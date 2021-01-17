package com.example.libcommon.util

import android.animation.Animator
import android.animation.TimeInterpolator
import java.net.URLEncoder

fun Any.TAG(): String {
    return javaClass.simpleName
}


fun Any.encode(code: String = "UTF-8") = URLEncoder.encode(this.toString(), code)

inline fun Any.measureTime(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    val end = System.currentTimeMillis()
    return end - start
}
