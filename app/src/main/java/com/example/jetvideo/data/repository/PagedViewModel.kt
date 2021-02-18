package com.example.jetvideo.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jetvideo.data.model.BaseViewModel
import com.example.libcommon.util.ext.logd

abstract class PagedViewModel<K, V>(key: K) : BaseViewModel() {

    val boundary = MutableLiveData<Boolean>()

    protected val config: PagedList.Config = PagedList
            .Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(12)
            .build()

    lateinit var dataSource: DataSource<K, V>

    private val factory = object : DataSource.Factory<K, V>() {
        override fun create(): DataSource<K, V> {
            if (!::dataSource.isInitialized || dataSource.isInvalid) dataSource = createDataSource()
            return dataSource
        }
    }

    private val boundaryCallback = object : PagedList.BoundaryCallback<V>() {

        override fun onZeroItemsLoaded() {
            logd("onZeroItemsLoaded")
            boundary.value = false
        }

        /*
        * 向前加载数据，完成
        * */
        override fun onItemAtFrontLoaded(itemAtFront: V) {
            super.onItemAtFrontLoaded(itemAtFront)
        }

        /*
        * 向后加载数据，完成
        * */
        override fun onItemAtEndLoaded(itemAtEnd: V) {
            // 数据加载完了（这里指的加载完是说从网络上也加载不到数据了，此时paging会认为数据已经没了）
            logd("onItemAtEndLoaded   ${itemAtEnd}")
            boundary.value = true
        }

    }

    open val pageList = LivePagedListBuilder<K, V>(factory, config)
            .setInitialLoadKey(key)
            .setBoundaryCallback(boundaryCallback)
            .build()

    abstract fun createDataSource(): DataSource<K, V>

}