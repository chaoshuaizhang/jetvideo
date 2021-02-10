package com.example.jetvideo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.R
import com.example.jetvideo.databinding.LayoutRefreshLoadBinding

class MoreStatusRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    val binding = LayoutRefreshLoadBinding.bind(this)

    val emptyView by lazy {
        binding.emptyView.viewStub?.inflate()
    }

    init {

    }

    fun <T, VH : RecyclerView.ViewHolder> addIAdapter(context: Context, iAdapter: IMoreStatusAdapter<T, VH>) {
        iAdapter.getAdapter()
        binding.recyclerview.layoutManager = iAdapter.getLayoutManager(context)
    }

}