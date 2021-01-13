package com.example.libnetwork.http

import okhttp3.Request

class GetRequest<T>(var url: String) : BaseRequest<T, GetRequest<T>>() {
    override fun generateRequest(): Request {
        url += "?"
        for ((k, v) in params) {
            url += "k=$k&v=$v&"
        }
        return Request.Builder().url(baseUrl + url).build()
    }
}