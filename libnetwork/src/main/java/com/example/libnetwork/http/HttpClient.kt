package com.example.libnetwork.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class HttpClient {

    companion object {

        private const val PPJoke = "http://10.234.1.141:8080/serverdemo/"

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

        /*
        * 发送类似form表单类型的参数
        * */
        fun <T> post(path: String = PPJoke) = PostRequest<T>(path)

        fun <T> get(path: String) = GetRequest<T>(PPJoke + path)

    }
}