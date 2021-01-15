package com.example.libnetwork.http

import android.util.Log
import com.example.libnetwork.db.entity.ApiResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class HttpClient {

    companion object {

        const val WAN_ANDROID = "https://wanandroid.com/"

        val INSTANCE by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        fun <T> getWanAndroidRequest(path: String) = GetRequest<T>(WAN_ANDROID + path)

        fun <T> postRequest(path: String) = PostRequest<T>(path)

        fun <T> getRequestCallback(path: String, cb: MyCallback<T>) {
            GetRequest<T>(path).enqueue(cb)
        }

    }

}