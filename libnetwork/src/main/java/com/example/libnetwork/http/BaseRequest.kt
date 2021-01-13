package com.example.libnetwork.http

import android.view.View
import com.alibaba.fastjson.JSONObject
import com.example.libnetwork.db.entity.WordDTO
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.ObservableEmitter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

/*
* 每个请求都会重新创建一个request，所以这里只存储和当前请求相关的东西
* */
abstract class BaseRequest<T, R> {

    val NET_ONLY = 1
    val CACHE_ONLY = 2
    val CACHE_FIRST = 3
    val NET_CACHE = 4

    val params = mutableMapOf<String, Any>()

    val baseUrl = ""

    private fun getCall(): Call {
        // 每次请求需要重新创建
        val request = generateRequest()
        return HttpClient.INSTANCE.newCall(request)
    }

    fun addParam(k: String, v: Any) = apply { params[k] = v }

    protected abstract fun generateRequest(): Request


    /*
    * 同步方式
    * */
    fun execute() {

    }

    /*
    * 异步方式
    * */
    fun enqueue(emitter: ObservableEmitter<T>) {
        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                emitter.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val parameterizedType = emitter.javaClass.genericSuperclass as ParameterizedType
                val type = parameterizedType.actualTypeArguments[0]
                val result = JSONObject.parseObject(response.body?.string(), type) as T
                emitter.onNext(result)
            }
        })
    }

}

interface MyCallback<T> {
    fun callback(t: T)
}

