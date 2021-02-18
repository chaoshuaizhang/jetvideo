package com.example.jetvideo.ui.base

import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.widget.IMoreStatusAdapter
import com.example.jetvideo.widget.WrapperRefreshLayout
import com.example.libcommon.util.ext.toast
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

typealias RecVH = RecyclerView.ViewHolder

abstract class AbsListFragment<T, VH : RecVH, VDB : ViewDataBinding>
    : BaseDataBindingFrag<VDB>(), OnRefreshLoadMoreListener {

    private val contentView by lazy {
        getRefreshLayout().apply {
            refreshLayout.setOnRefreshLoadMoreListener(this@AbsListFragment)
        }
    }
    private val mAdapter by lazy {
        getPagedAdapter().apply {
            contentView.setAdapter(this)
        }
    }
    private val status = MutableLiveData<IMoreStatusAdapter.Status>()
            .apply {
                observe(this@AbsListFragment, {
                    when (it) {
                        IMoreStatusAdapter.Status.NORMAL -> {
                            contentView.hideEmptyView()
                        }
                        IMoreStatusAdapter.Status.EMPTY -> {
                            contentView.showEmptyView()
                        }
                        IMoreStatusAdapter.Status.LOAD_EMPTY -> {
                            contentView.hideEmptyView()
                        }
                        else -> throw Exception("Can not support the status [$it]")
                    }
                })
            }

    fun submitList(list: PagedList<T>) {
        if ((list.size > 0).also { finishLoadRefresh(it) }) mAdapter.submitList(list)
    }

    abstract fun getPagedAdapter(): PagedListAdapter<T, VH>

    open fun supportLoadRefresh() = Pair(first = true, second = true)

    abstract fun getRefreshLayout(): WrapperRefreshLayout

    protected fun finishLoadRefresh(hasData: Boolean) {
        when {
            hasData -> {
                status.value = IMoreStatusAdapter.Status.NORMAL
            }
            mAdapter.currentList.isNullOrEmpty() -> {
                // 没有任何数据
                status.value = IMoreStatusAdapter.Status.EMPTY
            }
            else -> {
                // 之前有数据，但是新加载的没有数据
                status.value = IMoreStatusAdapter.Status.LOAD_EMPTY
            }
        }
        val refreshLayout = getRefreshLayout().refreshLayout
        if (refreshLayout.isLoading) {
            refreshLayout.finishLoadMore()
        }
        if (refreshLayout.isRefreshing) {
            refreshLayout.finishRefresh()
        }
    }

}