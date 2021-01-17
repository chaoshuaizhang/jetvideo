package com.example.libnetwork.http

import com.alibaba.fastjson.JSONObject
import com.example.libnetwork.db.entity.ApiResponse
import java.lang.reflect.Type

object JsonConvert {

    fun <T> convert(result: String, clazz: Class<T>): T {
        val apiResponse = JSONObject.parseObject(result, ApiResponse::class.java)
        return JSONObject.parseObject(apiResponse.data.toString(), clazz) as T
    }

    fun <T> convert(result: String, responseType: Type): T {
        val apiResponse = JSONObject.parseObject(result, ApiResponse::class.java)
        return JSONObject.parseObject(apiResponse.data.toString(), responseType) as T
    }

}