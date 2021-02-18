package com.example.jetvideo.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.R
import com.example.jetvideo.databinding.LayoutRefreshLoadBinding
import com.example.libcommon.util.ext.resetParams
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class WrapperRefreshLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    val binding = LayoutRefreshLoadBinding.inflate(LayoutInflater.from(context), this, true)

    var refreshLayout: SmartRefreshLayout
    var recyclerView: RecyclerView

    //    var emptyViewStub: ViewStub
    var emptyView: View

    init {
        refreshLayout = binding.smartRefreshLayout
        recyclerView = binding.recyclerview
//        emptyViewStub = binding.emptyViewStub.viewStub ?: throw Exception()
        emptyView = binding.emptyView
    }

    fun <T, VH : RecyclerView.ViewHolder> setAdapter(adapter: PagedListAdapter<T, VH>) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun <T, VH : RecyclerView.ViewHolder> addIAdapter(context: Context, iAdapter: IMoreStatusAdapter<T, VH>) {
        recyclerView.layoutManager = iAdapter.getLayoutManager(context)
    }

    fun showEmptyView(iconId: Int = 0, msg: String = "") {
//        if (emptyView == null) emptyView = emptyViewStub.inflate()
        refreshLayout.isVisible = false
        emptyView?.isVisible = true
    }

    fun hideEmptyView() {
        refreshLayout.isVisible = true
        emptyView?.apply { isVisible = false }
    }

}