package com.example.libnetwork.http

import okhttp3.Request

class PostRequest<T>(url: String) : BaseRequest<T, PostRequest<T>>(url) {

    override fun generateRequest(): Request.Builder {

        return Request.Builder().apply {
            if (fields != null && files != null) {
                addRequestBody(this)
            } else if (fields != null) {
                generateFormFields(this)
            } else if (files != null) {
                generateFile(this)
            }
        }.url(generateUrlParams())
    }

}