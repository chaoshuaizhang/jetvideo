package com.example.jetvideo.data.repository

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.jetvideo.dto.FeedEntity
import com.example.jetvideo.dto.FeedItemEntity
import com.example.libcommon.util.ext.logd
import com.example.libcommon.util.typeToken
import com.example.libnetwork.db.entity.ApiResponse
import com.example.libnetwork.http.*
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import javax.inject.Inject

/*
* repository是按照模块儿分类 还是 功能分类
* https://github.com/android/architecture-components-samples/blob/2c19434f89e925b8bea56366faa0a197c5b90b96/BasicSample/app/src/main/java/com/example/android/persistence/DataRepository.java
* */
class FeedRepository @Inject constructor() {

    val feedsCache = MutableLiveData<List<FeedItemEntity>>()

    fun loadFeeds(withCache: Boolean = false, feedId: Int = 0): List<FeedItemEntity> {
        val request = HttpClient.get<FeedEntity>("feeds/queryHotFeedsList")
                .addQuery("feedId", feedId)
                .addQuery("userId", 0)
                .addQuery("feedType", "all")
                .addQuery("pageCount", 3)
                .setConvertType(object : TypeToken<FeedEntity>() {}.type)
        if (withCache) {
            request.cacheStrategy(CACHE_ONLY).execute().let {
                if (it.status == 304) {
                    // 获取缓存成功
                    logd("获取缓存成功   ${it.data?.data?.size}")
                    feedsCache.postValue(it.data?.data)
                } else {
                    // 获取缓存失败
                    it.data?.data?.let { result -> return result }
                }
            }
        }
        // ==0 说明是第一页数据，需要缓存起来
        request.cacheStrategy(if (feedId == 0) NET_CACHE else NET_ONLY)
                .execute().data?.data?.let { return it }
        return emptyList()
    }

}