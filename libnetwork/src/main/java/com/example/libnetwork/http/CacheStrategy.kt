package com.example.libnetwork.http

import androidx.annotation.IntDef

// 只访问网络
const val NET_ONLY = 1

// 只访问缓存
const val CACHE_ONLY = 2

// 先访问网络，然后存储到缓存
const val NET_CACHE = 4

@Target(AnnotationTarget.TYPE)
@IntDef(value = [NET_ONLY, CACHE_ONLY, NET_CACHE])
annotation class CacheStrategy()
