package com.example.libnetwork.http

import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.example.libcommon.util.ext.logd
import com.example.libcommon.util.measureTime
import com.example.libnetwork.CacheManager
import com.example.libnetwork.db.CacheDatabase
import com.example.libnetwork.db.entity.ApiResponse
import com.example.libnetwork.db.entity.Cache
import com.example.libnetwork.util.UrlCreator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.Single
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.SocketTimeoutException

/*
* 每个请求都会重新创建一个request，所以这里只存储和当前请求相关的东西
* */
abstract class BaseRequest<T, R>(val url: String) {

    private var headers: MutableMap<String, String>? = null
    private var params: MutableMap<String, Any>? = null
    protected var fields: MutableMap<String, String>? = null
    protected var files: List<File>? = null
    lateinit var requestBody: RequestBody
    private var cacheStrategy = 0
    private var cacheKey: String? = null

    // 解析类型
    lateinit var convertType: Type
    lateinit var convertClass: Class<T>

    lateinit var convert: ResponseConvert<T>

    /*
    * 生成对应的request：get、post、patch、put ...
    * */
    protected abstract fun generateRequest(): Request.Builder

    private fun getCall(): Call {
        // 每次请求需要重新创建
        val request = generateRequest()
        // 添加header信息
        headers?.forEach {
            request.addHeader(it.key, it.value)
        }
        return HttpClient.INSTANCE.newCall(request.build())
    }

    fun addHeader(k: String, v: String) = apply {
        headers = (headers ?: mutableMapOf()).apply { put(k, v) }
    }

    /*
    * 添加拼接在url后面的参数
    * */
    fun addQuery(k: String, v: Any) = apply {
        // 只能添加基本类型，这块儿先不做校验了
        params = (params ?: mutableMapOf()).apply { put(k, v) }
    }

    /*
    * 拼接参数
    * */
    fun generateUrlParams() = UrlCreator.createUrlByParams(url, params)

    /*
    * 添加post的参数
    * */
    fun addField(k: String, v: String) = apply {
        fields = (fields ?: mutableMapOf()).apply {
            put(k, v)
        }
    }

    /*
    * 只post field类型
    * */
    fun generateFormFields(builder: Request.Builder) {
        fields?.let {
            val formBody = FormBody.Builder()
            it.forEach {
                formBody.add(it.key, it.value)
            }
            builder.post(formBody.build())
        }
    }

    /*
    * 只post file类型
    * */
    fun generateFile(builder: Request.Builder) {

        files?.let {
            builder.post(it[0].asRequestBody())
        }
    }

    /*
    * file 和 field混合post
    * */
    fun addRequestBody(builder: Request.Builder) {
        val multipartBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        fields?.forEach {
            multipartBody.addFormDataPart(it.key, it.value)
        }
        files?.forEach {
            multipartBody.addFormDataPart("file", it.name, it.asRequestBody("file".toMediaType()))
        }
        builder.post(multipartBody.build())
    }

    fun setConvertCallback(c: ResponseConvert<T>) = apply { convert = c }

    /*
    * 解析非泛型类型的数据
    * */
    fun setConvertType(clazz: Class<T>) = apply { convertClass = clazz }

    /*
    * 解析泛型类型数据：List<XXX>
    * */
    fun setConvertType(type: Type) = apply { convertType = type }

    fun cacheStrategy(strategy: @CacheStrategy Int) = apply { cacheStrategy = strategy }

    fun cacheKey(key: String) = apply { cacheKey = key }

    fun enqueue(): Observable<T> = Observable.create {
        enqueue(it)
    }

    /*
    * 异步方式
    * 注意：即使是异步，回调还是在子线程
    * */
    private fun enqueue(emitter: ObservableEmitter<T>) {
        if (cacheStrategy == CACHE_ONLY)
            CacheManager.queryCache<T>(cacheKey)?.let { result ->
                Log.d("TAGCacheManager", "enqueue: $result")
                emitter.onNext(result)
                emitter.onComplete()
            }

        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                emitter.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("onResponseTAG", "onResponse: ${Thread.currentThread().name}")
                parseResponse(response).let {
                    if (cacheStrategy != NET_ONLY) CacheManager.cache(cacheKey, it)
                    emitter.onNext(it)
                }
            }
        })
    }

    /*
    * 异步方式，通过回调接口的形式
    * */
    fun enqueue(cb: ResponseCallback<T>) {
        if (cacheStrategy == CACHE_ONLY)
            CacheManager.queryCache<T>(cacheKey)?.let { result ->
                Log.d("TAGCacheManager", "enqueue: $result")
                cb.onSuccess(result)
                return
            }
        getCall().enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                cb.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
//                val parameterizedType = cb.javaClass.genericSuperclass as ParameterizedType
//                val type = parameterizedType.actualTypeArguments[0]
//                val result = JSONObject.parseObject(response.body?.string(), type) as T
                val result = parseResponse(response)
                if (cacheStrategy != NET_ONLY) CacheManager.cache(cacheKey, result)
                cb.onSuccess(result)
            }
        })
    }

    /*
    * 同步请求，直接获得请求结果
    * */
    fun execute(): ApiResponse<T> {
        if (::convertType.isInitialized || ::convert.isInitialized) {
            try {
                if (cacheStrategy == CACHE_ONLY) {
                    CacheManager.queryCache<T>(cacheKey ?: autoGenerateCacheKey())?.let { result ->
                        return ApiResponse(304, true, null, result)
                    }
                }
                val result = parseResponse(getCall().execute())
                if (cacheStrategy != NET_ONLY) {
                    // 只要不是NET_ONLY，统一对结果进行缓存
                    CacheManager.cache(cacheKey ?: autoGenerateCacheKey(), result)
                }
                return ApiResponse(200, true, null, result)
            } catch (e: Exception) {
                e.printStackTrace()
                return ApiResponse(400, false, e.message, null)
            }
        } else {
            return ApiResponse(400, false, "未设置解析器", null)
        }
    }

    private fun parseResponse(response: Response): T {
        if (response.isSuccessful) {
            response.body?.let { body ->
                // TODO: 2021/1/19/019 这块儿的逻辑是否可以下沉，放在这有可能不符合开闭原则
                return when {
                    ::convert.isInitialized -> {
                        convert.convert(body.string())
                    }
                    ::convertType.isInitialized -> {
                        JsonConvert.convert(body.string(), convertType) as T
                    }
                    ::convertClass.isInitialized -> {
                        JsonConvert.convert(body.string(), convertClass)
                    }
                    else -> throw Exception("未设置解析器")
                }
            }
        }
        throw Exception("response 失败")
    }

    private fun cacheResponse(result: T) {
        if (cacheStrategy != NET_ONLY) {
            (cacheKey ?: autoGenerateCacheKey()).apply {
                CacheManager.cache(this, result)
            }
        }
    }

    private fun autoGenerateCacheKey() = UrlCreator.createUrlByParams(url, params)

}



