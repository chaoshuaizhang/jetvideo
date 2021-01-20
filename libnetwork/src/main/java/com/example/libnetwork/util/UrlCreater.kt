package com.example.libnetwork.util

import com.example.libcommon.util.encode
import java.net.URLEncoder

object UrlCreator {

    fun createUrlByParams(url: String, params: MutableMap<String, Any>?): String {
        params?.let {
            var tmpUrl = "$url?"
            it.forEach { entry ->
                tmpUrl += "${entry.key}=${entry.value.encode()}&"
            }
            return tmpUrl.removeRange(tmpUrl.length - 1, tmpUrl.length)
        }
        return url
    }

}