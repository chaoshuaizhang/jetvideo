package com.example.jetvideo.data.repository

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList

class MyDataSource<K, V>(var list: List<V> = emptyList()) : PageKeyedDataSource<K, V>() {

    lateinit var pagedList: PagedList<V>

    @SuppressLint("RestrictedApi")
    fun buildPagedList(config: PagedList.Config): PagedList<V> {
        if (!::pagedList.isInitialized) {
            pagedList = PagedList.Builder(this, config)
                    .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
                    .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
                    .build()
        }
        return pagedList
    }

    override fun loadInitial(params: LoadInitialParams<K>, callback: LoadInitialCallback<K, V>) {
        callback.onResult(list, null, null)
    }

    override fun loadBefore(params: LoadParams<K>, callback: LoadCallback<K, V>) {
        callback.onResult(emptyList(), null)
    }

    override fun loadAfter(params: LoadParams<K>, callback: LoadCallback<K, V>) {
        callback.onResult(emptyList(), null)
    }
}