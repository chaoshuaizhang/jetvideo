package com.example.jetvideo.data.model

import androidx.arch.core.util.Function
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.example.jetvideo.data.repository.FeedRepository
import com.example.jetvideo.data.repository.MyDataSource
import com.example.jetvideo.data.repository.PagedViewModel
import com.example.jetvideo.dto.FeedItemEntity
import com.example.libcommon.util.ext.logd
import javax.inject.Inject

class FeedViewModel @Inject constructor(val feedRepository: FeedRepository)
    : PagedViewModel<Int, FeedItemEntity>(0) {

    val feedsCache = Transformations.map(feedRepository.feedsCache) { input -> // 输入的是List，输出的是PagedList
        MyDataSource<Int, FeedItemEntity>(input)
                .buildPagedList(config)

    }

    inner class FeedDataSource : ItemKeyedDataSource<Int, FeedItemEntity>() {

        // 根据当前列表的最后一个元素的id，查找下一个列表
        override fun getKey(item: FeedItemEntity): Int {
            logd("getKey  ${Thread.currentThread().name}")
            return item.id ?: 0
        }

        // FIXME: 2021/2/16/016 loadInitial中不能使用异步，否则页面无法渲染
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<FeedItemEntity>) {
            callback.onResult(feedRepository.loadFeeds(true, 0))
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<FeedItemEntity>) {
            logd("loadAfter   ${params.key}   ${Thread.currentThread().name}")
            callback.onResult(feedRepository.loadFeeds(feedId = params.key))
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<FeedItemEntity>) {
            callback.onResult(emptyList())
            logd("loadBefore   ${params.key}   ${Thread.currentThread().name}")
        }
    }

    override fun createDataSource() = FeedDataSource()

}