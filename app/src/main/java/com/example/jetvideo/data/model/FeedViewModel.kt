package com.example.jetvideo.data.model

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.example.jetvideo.data.repository.FeedRepository
import com.example.jetvideo.data.repository.MyDataSource
import com.example.jetvideo.data.repository.PagedViewModel
import com.example.jetvideo.dto.FeedItemEntity
import com.example.libcommon.util.ext.logd
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FeedViewModel @Inject constructor(val feedRepository: FeedRepository)
    : PagedViewModel<Int, FeedItemEntity>(0) {

    val feeds = mutableListOf<FeedItemEntity>()

    val feedsCache = Transformations.map(feedRepository.feedsCache) { input -> // 输入的是List，输出的是PagedList
        MyDataSource<Int, FeedItemEntity>(input)
            .buildPagedList(config)
    }

    var feedsLoadMore = MutableLiveData<PagedList<FeedItemEntity>>()

    @SuppressLint("RestrictedApi")
    fun loadMore(key: Int) {
        Single.create<List<FeedItemEntity>> {
            val newList = (pageList.value?.toList()
                ?: emptyList<FeedItemEntity>()) + feedRepository.loadFeeds(feedId = key)
            it.onSuccess(newList)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { loadedData ->
                feeds.clear()
                feeds.addAll(loadedData)
                // 置原来的主PagedList无效
                dataSource.invalidate()
                // 暂时不用这种方式了
                // feedsLoadMore.value = MyDataSource<Int, FeedItemEntity>(loadedData).buildPagedList(config)
            }
    }

    inner class FeedDataSource : ItemKeyedDataSource<Int, FeedItemEntity>() {

        // 根据当前列表的最后一个元素的id，查找下一个列表
        override fun getKey(item: FeedItemEntity): Int {
            logd("getKey  ${Thread.currentThread().name}")
            return item.id ?: 0
        }

        // FIXME: 2021/2/16/016 loadInitial中不能使用异步，否则页面无法渲染
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<FeedItemEntity>) {
            if (feeds.isNotEmpty()) callback.onResult(feeds)
            else callback.onResult(feedRepository.loadFeeds(true, 0))
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<FeedItemEntity>) {
            logd("loadAfter   ${params.key}   ${Thread.currentThread().name}")
            // FIXME: 2021/2/20 Paging自带的上拉加载与我们自己Recyclerview的上拉加载会有冲突，需要处理下 [进行一下同步]
            // callback.onResult(feedRepository.loadFeeds(feedId = params.key))
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<FeedItemEntity>) {
            callback.onResult(emptyList())
            logd("loadBefore   ${params.key}   ${Thread.currentThread().name}")
        }
    }

    override fun createDataSource() = FeedDataSource()

}