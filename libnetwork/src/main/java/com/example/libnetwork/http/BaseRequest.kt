package com.example.libnetwork.http

import android.util.Log
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.example.libnetwork.db.entity.WordDTO
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

/*
* 每个请求都会重新创建一个request，所以这里只存储和当前请求相关的东西
* */
abstract class BaseRequest<T, R> {

    val NET_ONLY = 1
    val CACHE_ONLY = 2
    val CACHE_FIRST = 3
    val NET_CACHE = 4

    val baseUrl = ""
    val params = mutableMapOf<String, Any>()
    lateinit var mType: Type

    protected abstract fun generateRequest(): Request

    private fun getCall(): Call {
        // 每次请求需要重新创建
        val request = generateRequest()
        return HttpClient.INSTANCE.newCall(request)
    }

    fun addParam(k: String, v: Any) = apply { params[k] = v }

    fun setConvertType(type: Type) = apply { mType = type }

    fun enqueue(): Observable<T> = Observable.create {
        Log.d("onResponseTAG---", "getRequestWithCb: ${Thread.currentThread().name}")
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
                val res = JSONObject.parseObject(response.body?.string(), type) as T
                println(res)
                cb.onSuccess(res)
            }
        })
    }

    private fun parseResponse(emitter: ObservableEmitter<T>, response: Response) {
        if (response.isSuccessful && ::mType.isInitialized) {
            val result = JSONObject.parseObject(response.body?.string(), mType) as T
            emitter.onNext(result)
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

