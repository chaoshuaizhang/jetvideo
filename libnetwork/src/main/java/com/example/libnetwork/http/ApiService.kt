package com.example.libnetwork.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiService {

    companion object {

        private val okHttpClient: OkHttpClient

        // 定义日志拦截器，打印出请求、返回信息，便于排错
        private val httpLoggingInterceptor = HttpLoggingInterceptor()

        init {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                    .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .build()

            val trustManager = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }

            })
            // https握手流程
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustManager, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)

        }

        fun a() {
        }
    }
}