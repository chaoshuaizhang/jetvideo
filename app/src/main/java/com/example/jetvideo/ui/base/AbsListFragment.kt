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

typealias RecVH = RecyclerView.ViewHolder

abstract class AbsListFragment<T, VH : RecVH, VDB : ViewDataBinding> : BaseDataBindingFrag<VDB>() {

    lateinit var contentView: WrapperRefreshLayout
    private val mAdapter by lazy {
        getPagedAdapter()
    }
    private val status = MutableLiveData<IMoreStatusAdapter.Status>()

    override fun initData() {
        status.observe(this, {
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

    abstract fun loadMore()

    abstract fun refresh()

    private fun finishLoadRefresh(hasData: Boolean) {
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
    }

}