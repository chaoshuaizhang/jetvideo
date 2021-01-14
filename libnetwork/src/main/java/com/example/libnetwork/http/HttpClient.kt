package com.example.libnetwork.http

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class HttpClient {

    companion object {
        val INSTANCE by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        fun <T> getRequest(url: String) = GetRequest<T>(url)

        fun <T> getRequestCallback(url: String, cb: MyCallback<T>) {
            GetRequest<T>(url).enqueue(cb)
        }

    }

}