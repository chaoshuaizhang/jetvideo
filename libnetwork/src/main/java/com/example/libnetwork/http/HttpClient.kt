package com.example.libnetwork.http

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

        fun getRequest(): GetRequest {

        }

    }

}