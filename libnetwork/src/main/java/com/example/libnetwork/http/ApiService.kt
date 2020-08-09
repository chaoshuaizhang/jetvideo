package com.example.libnetwork.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class ApiService {

    companion object {

        val okHttpClient: OkHttpClient

        // 定义日志拦截器，打印出请求、返回信息，便于排错
        private val httpLoggingInterceptor = HttpLoggingInterceptor()

        init {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                    .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

            val trustManager = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            })
            // https握手流程
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustManager, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
            // 信任所有的证书（这块儿使用lambda代替kotlin匿名内部类）
            // android9.0及以后默认不能发送http请求，必须发送https的，包括webview
            // 在manifest文件中配置usesCleartextTraffic="true"来允许http明文请求
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
        }
    }
}