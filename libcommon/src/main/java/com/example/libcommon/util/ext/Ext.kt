package com.example.libcommon.util

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.provider.Settings
import android.util.TypedValue
import com.example.libcommon.AppGlobal
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

fun Double.dp(): Double {
    val density = AppGlobal.app.resources.displayMetrics.density
    return density * this + 0.5
}

val Number.dp: Int
    get() = (AppGlobal.app.resources.displayMetrics.density * toFloat() + 0.5f).toInt()

val Number.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, toFloat(),
            AppGlobal.app.resources.displayMetrics)
