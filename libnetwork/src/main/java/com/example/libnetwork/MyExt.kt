package com.example.libnetwork

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

inline fun Call.myEnqueue(
    crossinline onResponse: (Call, Response) -> Unit = { _, _ -> },
    crossinline onFailure: (Call, IOException) -> Unit = { _, _ -> }
): Callback {
    return object : Callback {
        // 要想这么用，需要 + crossinline
        override fun onFailure(call: Call, e: IOException) = onFailure(call, e)

        override fun onResponse(call: Call, response: Response) = onResponse(call, response)
    }
}