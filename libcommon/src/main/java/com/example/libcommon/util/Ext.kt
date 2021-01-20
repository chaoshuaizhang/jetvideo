package com.example.libcommon.util

import android.animation.Animator
import android.animation.TimeInterpolator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

/*
*reified声明的类型参数不会在运行时擦除
* */
inline fun <reified T : Any> Gson.fromJson(resStr: String) {
    fromJson<T>(resStr, object : TypeToken<T>() {}.type)
}

fun <T> typeToken() = object : TypeToken<T>() {}.type
