package com.example.libnetwork.http.impl

import com.example.libnetwork.http.Request
import okhttp3.FormBody

/*
* Post请求的基类
* */
class PostRequest<T>(url: String) : Request<PostRequest<T>, T>(url) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val formBody = FormBody.Builder()
        params.forEach { (k, v) ->
            formBody.add(k, v as String)
        }
        return builder.post(formBody.build()).url(url).build()
    }
}