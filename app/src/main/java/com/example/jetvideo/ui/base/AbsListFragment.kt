package com.example.jetvideo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.databinding.LayoutRefreshLoadBinding
import com.example.jetvideo.widget.IMoreStatusAdapter
import com.example.jetvideo.widget.MoreStatusRecyclerView

abstract class AbsListFragment<T> : BaseDataBindingFrag<LayoutRefreshLoadBinding>() {

    lateinit var mAdapter: PagedListAdapter<T, RecyclerView.ViewHolder>

    override fun getDataBinding(inflater: LayoutInflater?, container: ViewGroup?): LayoutRefreshLoadBinding {
        return context?.let {
            DataBindingUtil.getBinding(MoreStatusRecyclerView(it))
        } ?: throw Exception()
    }

    override fun initView() {
        binding.smartRefreshLayout.apply {
            // 是否支持加载、刷新
            val loadRefresh = supportLoadRefresh()
            setEnableLoadMore(loadRefresh.first)
            setEnableRefresh(loadRefresh.second)
            setOnRefreshListener {
                refresh()
            }
            setOnLoadMoreListener { loadMore() }
        }
        val m = (binding.root as MoreStatusRecyclerView)
        mAdapter = getAAAdapter()
        m.addIAdapter(requireContext(), object : IMoreStatusAdapter<T, RecyclerView.ViewHolder> {
            override fun getAdapter(): PagedListAdapter<T, RecyclerView.ViewHolder> {
                return mAdapter
            }

            override fun showEmptyView() {
                m.emptyView?.isVisible = true
            }

            override fun showNetErrorView() {
                TODO("Not yet implemented")
            }

            override fun showContentView() {
                TODO("Not yet implemented")
            }

        })
    }

    fun submitList(list: PagedList<T>) {
        if ((list.size > 0).also { finishLoadRefresh(it) }) mAdapter.submitList(list)
    }

    open fun supportLoadRefresh() = Pair(first = true, second = true)

    abstract fun <T, VH : RecyclerView.ViewHolder> getAAAdapter(): PagedListAdapter<T, VH>

    abstract fun loadMore()

    abstract fun refresh()

    private fun finishLoadRefresh(hasData: Boolean) {

    }

}