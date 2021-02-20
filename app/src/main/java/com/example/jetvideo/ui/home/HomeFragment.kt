package com.example.jetvideo.ui.home

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.ItemKeyedDataSource
import com.example.jetvideo.R
import com.example.jetvideo.adapter.FeedAdapter
import com.example.jetvideo.adapter.FeedVH
import com.example.jetvideo.data.model.FeedViewModel
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.dto.FeedItemEntity
import com.example.jetvideo.ui.base.AbsListFragment
import com.example.jetvideo.widget.WrapperRefreshLayout
import com.example.libcommon.util.ext.logd
import com.scwang.smart.refresh.layout.api.RefreshLayout
import dagger.hilt.android.AndroidEntryPoint
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
            if ((it.size != 0).also { finishLoadRefresh(it) }) {
                submitList(it)
            }
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        // 清除引用的数据
        viewModel.feeds.clear()
        viewModel.dataSource.invalidate()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mAdapter.currentList?.lastOrNull()?.let { it ->
            viewModel.loadMore(it.id!!)
        }
    }

    override fun getPagedAdapter() = FeedAdapter(requireContext(), "HOME")

    override fun getRefreshLayout(): WrapperRefreshLayout {
        return binding.refreshLayout
    }

}