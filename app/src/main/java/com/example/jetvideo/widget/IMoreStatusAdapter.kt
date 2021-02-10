package com.example.jetvideo.widget

import android.content.Context
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface IMoreStatusAdapter<T, VH : RecyclerView.ViewHolder> {

    fun getAdapter(): PagedListAdapter<T, VH>
    fun getLayoutManager(context: Context) = LinearLayoutManager(context)

    fun setStatus(status: Status) {
        when (status) {
            Status.NORMAL -> {
                showContentView()
            }
            Status.EMPTY -> {
                showEmptyView()
            }
        }
    }

    fun showEmptyView()
    fun showContentView()

    enum class Status {
        NORMAL,
        EMPTY,
        LOAD_EMPTY
    }

}