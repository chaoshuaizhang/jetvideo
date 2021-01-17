package com.example.libnetwork.http

import okhttp3.Request

class GetRequest<T>(url: String) : BaseRequest<T, GetRequest<T>>(url) {

    override fun generateRequest() = Request.Builder().url(generateUrlParams())
}