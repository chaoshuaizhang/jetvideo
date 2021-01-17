package com.example.libnetwork.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


class HttpClient {

    companion object {

        const val WAN_ANDROID = "https://wanandroid.com/"

        val INSTANCE by lazy {
            OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build().apply {
                        val trustManager = arrayOf<TrustManager>(object : X509TrustManager {
                            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                            }

                            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                            }

                            override fun getAcceptedIssuers() = arrayOfNulls<X509Certificate>(0)
                        })
                        val sslInstance = SSLContext.getInstance("SSL")
                        sslInstance.init(null, trustManager, SecureRandom())
                        HttpsURLConnection.setDefaultSSLSocketFactory(sslInstance.socketFactory)
                        // 信任所有证书
                        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
                    }
        }

        fun <T> getWanAndroidRequest(path: String) = GetRequest<T>(WAN_ANDROID + path)

        /*
        * 发送类似form表单类型的参数
        * */
        fun <T> postParamsRequest(path: String) = PostRequest<T>(path)

        fun <T> getRequestCallback(path: String, cb: MyCallback<T>) {
            GetRequest<T>(path).enqueue(cb)
        }

    }
}