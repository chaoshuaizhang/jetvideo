package com.example.jetvideo.ui.home

import com.example.jetvideo.R
import com.example.jetvideo.adapter.FeedAdapter
import com.example.jetvideo.adapter.FeedVH
import com.example.jetvideo.data.model.FeedViewModel
import com.example.jetvideo.data.repository.MyDataSource
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.dto.FeedItemEntity
import com.example.jetvideo.ui.base.AbsListFragment
import com.example.jetvideo.widget.WrapperRefreshLayout
import com.example.libcommon.util.ext.logd
import com.example.libcommon.util.ext.toast
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : AbsListFragment<FeedItemEntity, FeedVH, FragHomeBinding>() {

    @Inject
    lateinit var viewModel: FeedViewModel

    override fun getLayoutId() = R.layout.frag_home

    override fun initView() {
    }

    override fun initData() {
        viewModel.pageList.observe(this, { submitList(it) })
        viewModel.boundary.observe(this, {
            logd("finishLoadRefresh")
            finishLoadRefresh(it)
        })
        // 缓存
        viewModel.feedsCache.observe(this) {
            submitList(it)
        }
        // 加载更多
        viewModel.feedsLoadMore.observe(this) {
            if (it.size == 0) finishLoadRefresh(false)
            else {
                val curList = mAdapter.currentList
                curList?.addAll(it)
                curList?.let { l -> submitList(l) }
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        viewModel.dataSource.invalidate()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mAdapter.currentList?.lastOrNull()?.let {
            runBlocking {
                viewModel.loadMore(it.id!!).collect {
                    MyDataSource<Int, FeedItemEntity>().buildPagedList()
                }
            }
        }
    }

    override fun getPagedAdapter() = FeedAdapter(requireContext(), "HOME")

    override fun getRefreshLayout(): WrapperRefreshLayout {
        return binding.refreshLayout
    }

}