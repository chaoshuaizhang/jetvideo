package com.example.libnetwork.http

import androidx.arch.core.executor.ArchTaskExecutor
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.libnetwork.cache.CacheManager
import com.example.libnetwork.cache.CacheStrategy
import com.example.libnetwork.util.CheckUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

// 这就属于顶级变量，不被包含在任意类或函数中
const val CACHE_ONLY = 1
// TODO: 2020/8/6/006 这块儿作者说的是并发访问本地和网络，然后缓存到本地
// 先访问缓存，在访问网络
const val CACHE_FIRST = 2
const val NET_ONLY = 3
// 请求网络后，结果保存在本地
const val NET_CACHE = 4

/*
* R：表示request的子类（每一种请求方式对应一个request：Get、Post）
* T：表示response中的实体类型
* */
abstract class Request<R, T>(val url: String) {

    var cacheKey: String? = null
    val headers = HashMap<String, String>()
    // any表示8种基本类型
    val params = HashMap<String, Any>()

    lateinit var type: Type

    @CacheStrategy
    var cacheStrategy = NET_ONLY

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        // TODO: 2020/8/6/006 这块儿用kotlin的反射不知道怎么实现
        if (CheckUtil.validBaseJavaType(value)) {
            params[key] = value;
        }
        return this as R
    }

    /*
    * 异步请求
    * */
    fun execute(cb: ((ApiResponse<T>) -> Unit), errorcb: ((String) -> Unit)?) {

        /*
        * 下面有几个同步的if判断，所以如果每个if都满足，就可能出现这种情况：
        * 先从缓存中取 --- 更新UI
        * 再从网络取 --- 覆盖刚刚更新的UI
        * */

        // 如果支持缓存
        if (cacheStrategy != NET_ONLY) {
            ArchTaskExecutor.getIOThreadExecutor().execute {
                val cache = getCache()
                cache?.let {
                    println("从缓存取")
                    cb(cache)
                }
            }
        }

        // 如果不是仅支持缓存
        if (cacheStrategy != CACHE_ONLY) {
            println("从网络取")
            getCall().enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    errorcb!!(e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    val resp = parseResponse(response, cb)
                    if (resp.success!!) {
                        cb(resp)
                    } else {
                        errorcb!!(resp.message!!)
                    }
                }
            })
        }
    }

    /*
    * 同步请求
    * */
    fun execute(): ApiResponse<T> {
        if (cacheStrategy != NET_ONLY) {
            return getCache()
        }
        if(cacheStrategy != CACHE_ONLY){
            var response = getCall().execute()
            return parseResponse(response, null)
        }
        return null!!
    }

    fun createUrlByParams(params: HashMap<String, Any>): String {

        return if (params.size != 0) {
            val sb = StringBuilder(url)
            sb.append("?")
            params.forEach { (k, v) ->
                sb.append("$k=$v&")
            }
            sb.deleteCharAt(sb.length - 1)
            sb.toString()
        } else {
            url
        }
    }

    fun setCacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    private fun generateKey(): String {
        cacheKey = createUrlByParams(params)
        return cacheKey!!
    }

    private fun getCall(): Call {
        val builder = Request.Builder()
        headers.forEach { (k, v) ->
            builder.addHeader(k, v)
        }
        val request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    fun parseResponse(response: Response, cb: ((ApiResponse<T>) -> Unit)?): ApiResponse<T> {
        val apiResponse = ApiResponse<T>(response.isSuccessful, response.code, null, null)
        try {
            if (response.isSuccessful) {
                val obj = JSONObject.parseObject(response.body.toString())
                val data = obj.getJSONObject("data")
                if (data != null) {
                    // 解析出实际我们需要的类型

                    //[1] 异步请求
                    if (cb != null) {
                        val type: ParameterizedType = cb.javaClass.genericSuperclass as ParameterizedType
                        // 后台的数据格式是：data[是一个对象]里边有个data[可能是对象，亦可能是数组]
                        apiResponse.data = JSON.parseObject(data["data"].toString(), type.actualTypeArguments[0]) as T
                    } else if (::type.isInitialized) {
                        // [2] 同步请求。【此处存在的一个问题是】
                        apiResponse.data = JSON.parseObject(data["data"].toString(), type)
                    }
                    // 请求成功，存入缓存
                    if (cacheStrategy != NET_ONLY) {
                        saveCache(apiResponse.data)
                    }
                } else {
                    apiResponse.message = response.body.toString()
                }
            } else {
                apiResponse.success = false
                apiResponse.message = "请求失败"
            }
        } catch (e: Exception) {
            apiResponse.success = false
            apiResponse.message = e.message
        }
        return apiResponse
    }

    private fun saveCache(data: T?) {
        cacheKey = cacheKey ?: createUrlByParams(params)
        CacheManager.save(cacheKey!!, data)
    }

    private fun getCache(): ApiResponse<T> {
        cacheKey = cacheKey ?: createUrlByParams(params)
        return ApiResponse(true, 304,
                "缓存获取成功", CacheManager.get(cacheKey!!))
    }

    /*
    * 用于json解析时获取数据类型
    * */
    fun setType(type: Type): R {
        this.type = type
        return this as R
    }

    /*
    * 不同的子类对request的实现不一样
    * */
    abstract fun generateRequest(builder: Request.Builder): Request

}