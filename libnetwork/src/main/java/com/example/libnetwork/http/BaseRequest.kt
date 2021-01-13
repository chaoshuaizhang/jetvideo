package com.example.libnetwork.http

import android.view.View
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/*
* 每个请求都会重新创建一个request，所以这里只存储和当前请求相关的东西
* */

class BaseRequest<T, R> {

    val NET_ONLY = 1
    val CACHE_ONLY = 2
    val CACHE_FIRST = 3
    val NET_CACHE = 4

    private fun getCall(): Call {
        // 每次请求需要重新创建
        val request = Request.Builder()
            .url("")
            .build()
        return HttpClient.INSTANCE.newCall(request)
    }

    /*
    * 同步方式
    * */
    fun execute() {

    }

    /*
    * 异步方式
    * */
    fun enqueue(callback: MyCallback<T>) {
        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val type = callback.javaClass.genericSuperclass as ParameterizedType
                type.actualTypeArguments[0]
            }
        })
    }

}

interface MyCallback<T> {
    fun callback(t: T)
}

