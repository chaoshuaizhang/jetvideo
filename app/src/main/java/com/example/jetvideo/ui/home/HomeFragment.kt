package com.example.jetvideo.ui.home

import androidx.fragment.app.viewModels
import androidx.paging.PagedListAdapter
import com.example.jetvideo.R
import com.example.jetvideo.adapter.FeedVH
import com.example.jetvideo.data.model.HomeViewModel
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.dto.FeedItemEntity
import com.example.jetvideo.ui.base.AbsListFragment

class HomeFragment : AbsListFragment<FeedItemEntity, FeedVH, FragHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()

    override fun getLayoutId() = R.layout.frag_home

    override fun initView() {
    }

    override fun initData() {

    }

    override fun getPagedAdapter(): PagedListAdapter<FeedItemEntity, FeedVH> {
        TODO("Not yet implemented")
    }

    override fun loadMore() {
        TODO("Not yet implemented")
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }

}