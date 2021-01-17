package com.example.libnetwork.http

import androidx.annotation.IntDef


const val NET_ONLY = 1
const val CACHE_ONLY = 2
const val CACHE_FIRST = 3

// 先访问网络，然后存储到网络
const val NET_CACHE = 4

@Target(AnnotationTarget.TYPE)
@IntDef(value = [NET_ONLY, CACHE_ONLY, CACHE_FIRST, NET_CACHE])
annotation class CacheStrategy()
