package com.example.jetvideo.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CommonAdapter<VH : RecyclerView.ViewHolder, T : Any>(val data: List<T>) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return generateViewHolder(viewType)
    }

    abstract fun generateViewHolder(viewType: Int): VH

    abstract override fun onBindViewHolder(holder: VH, position: Int)

    override fun getItemCount() = data.size
}