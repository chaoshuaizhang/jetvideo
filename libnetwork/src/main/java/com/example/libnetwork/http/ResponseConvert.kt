package com.example.libnetwork.http

interface ResponseConvert<T> {
    fun convert(result: String): T
}