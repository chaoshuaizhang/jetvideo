package com.example.libnetwork.http

import okhttp3.Request

class PostRequest<T>(private val url: String) : BaseRequest<T, PostRequest<T>>() {

    override fun generateRequest(): Request {

        return Request.Builder().apply {

        }.url(generateUrlParams(url)).build()
    }

}