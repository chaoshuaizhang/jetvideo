package com.example.libnetwork.http

import okhttp3.Request

class GetRequest<T>(var url: String) : BaseRequest<T, GetRequest<T>>() {

    override fun generateRequest(): Request {
        return Request.Builder().url(generateUrlParams(url)).build()
    }

}