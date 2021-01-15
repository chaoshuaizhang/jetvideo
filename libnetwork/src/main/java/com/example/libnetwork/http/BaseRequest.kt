package com.example.libnetwork.http

import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.example.libcommon.util.measureTime
import com.example.libnetwork.db.entity.ApiResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import okhttp3.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/*
* 每个请求都会重新创建一个request，所以这里只存储和当前请求相关的东西
* */
abstract class BaseRequest<T, R> {

    val NET_ONLY = 1
    val CACHE_ONLY = 2
    val CACHE_FIRST = 3
    val NET_CACHE = 4

    private lateinit var params: MutableMap<String, Any>
    lateinit var requestBody: RequestBody
    lateinit var mType: Type

    protected abstract fun generateRequest(): Request

    private fun getCall(): Call {
        // 每次请求需要重新创建
        val request = generateRequest()
        return HttpClient.INSTANCE.newCall(request)
    }

    fun addParam(k: String, v: Any) = apply {
        if (!::params.isInitialized) {
            params = mutableMapOf()
        }
        params[k] = v
    }

    fun generateUrlParams(url: String): String {
        if (!::params.isInitialized) return url
        var tmp = url
        tmp += "?"
        for ((k, v) in params) {
            tmp += "k=$k&v=$v&"
        }
        return tmp.removeRange(tmp.length - 1, tmp.length - 1)
    }

    fun addRequestBody(body: RequestBody) = apply {
        requestBody = body
    }

    fun setConvertType(type: Type) = apply { mType = type }

    fun enqueue(): Observable<T> = Observable.create {
        enqueue(it)
    }

    /*
    * 异步方式
    * 注意：即使是异步，回调还是在子线程
    * */
    private fun enqueue(emitter: ObservableEmitter<T>) {
        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                emitter.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("onResponseTAG", "onResponse: ${Thread.currentThread().name}")
                parseResponse(emitter, response)
            }
        })
    }

    /*
    * 异步方式，通过回调接口的形式
    * */
    fun enqueue(cb: MyCallback<T>) {
        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                cb.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val parameterizedType = cb.javaClass.genericSuperclass as ParameterizedType
                val type = parameterizedType.actualTypeArguments[0]
                val result = JSONObject.parseObject(response.body?.string(), type) as T
                cb.onSuccess(result)
            }
        })
    }

    private fun parseResponse(emitter: ObservableEmitter<T>, response: Response) {
        if (response.isSuccessful && ::mType.isInitialized) {
            measureTime {
                val apiResponse = JSONObject.parseObject(response.body?.string(), ApiResponse::class.java)
                val result = JSONObject.parseObject(apiResponse.data.toString(), mType) as T
                emitter.onNext(result)
            }.let {
                Log.d("getRequestWithCb", "parseResponse解析时间: $it")
            }
        } else {
            emitter.onError(Exception("暂时需要传入一个类型 TypeToken / TypeReference"))
        }
    }

}

/*
* 用抽象类的原因是：需要获取到泛型类型
* */
abstract class MyCallback<T> {

    abstract fun onSuccess(t: T)

    abstract fun onError(e: Exception)
}

