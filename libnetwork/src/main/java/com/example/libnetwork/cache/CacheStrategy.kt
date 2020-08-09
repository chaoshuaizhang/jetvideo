package com.example.libnetwork.cache

import androidx.annotation.IntDef
import com.example.libnetwork.http.CACHE_FIRST
import com.example.libnetwork.http.CACHE_ONLY
import com.example.libnetwork.http.NET_CACHE
import com.example.libnetwork.http.NET_ONLY

@IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
annotation class CacheStrategy