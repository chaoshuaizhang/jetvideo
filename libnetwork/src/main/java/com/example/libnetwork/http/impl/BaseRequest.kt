package com.example.libnetwork.http.impl

import android.view.View
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class BaseRequest {

    fun testRequest() {
        // 单例
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        // 每次请求需要重新创建
        val request = Request.Builder()
            .url("")
            .build()


        client.newCall(request).myEnqueue(this::onResponse, this::onFailure)

    }

    private fun onResponse(call: Call, response: Response) {

    }

    private fun onFailure(call: Call, e: IOException) {

    }

    inline fun Call.myEnqueue(
        crossinline onResponse: (Call, Response) -> Unit = { _, _ -> },
        crossinline onFailure: (Call, IOException) -> Unit = { _, _ -> }
    ): Callback {
        return object : Callback {
            // 要想这么用，需要 + crossinline
            override fun onFailure(call: Call, e: IOException) = onFailure(call, e)

            override fun onResponse(call: Call, response: Response) = onResponse(call, response)
        }
    }

}

