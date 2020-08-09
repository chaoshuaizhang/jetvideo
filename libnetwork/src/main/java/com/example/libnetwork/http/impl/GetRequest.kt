package com.example.libnetwork.http.impl

import com.example.libnetwork.http.Request

class GetRequest<T>(url: String) : Request<GetRequest<T>, T>(url) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {

        return builder.get().url(createUrlByParams(params)).build()

    }

}