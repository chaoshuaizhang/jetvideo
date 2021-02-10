package com.example.jetvideo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.R
import com.example.jetvideo.databinding.LayoutRefreshLoadBinding
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class WrapperRefreshLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    val binding = LayoutRefreshLoadBinding.bind(this)

    var refreshLayout: SmartRefreshLayout
    var recyclerView: RecyclerView
    var emptyViewStub: ViewStub
    var emptyView: View? = null

    init {
        refreshLayout = binding.smartRefreshLayout
        recyclerView = binding.recyclerview
        emptyViewStub = binding.emptyViewStub.viewStub ?: throw Exception()
    }

    fun <T, VH : RecyclerView.ViewHolder> setMyAdapter(adapter: PagedListAdapter<T, VH>) {
        recyclerView.adapter = adapter
    }

    fun <T, VH : RecyclerView.ViewHolder> addIAdapter(context: Context, iAdapter: IMoreStatusAdapter<T, VH>) {
        recyclerView.layoutManager = iAdapter.getLayoutManager(context)
    }

    fun showEmptyView(iconId: Int = 0, msg: String = "") {
        if (emptyView == null) emptyView = emptyViewStub.inflate()
        emptyView?.isVisible = true
    }

    fun hideEmptyView() {
        emptyView?.let { isVisible = true }
    }

}