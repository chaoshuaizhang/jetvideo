package com.example.jetvideo.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.jetvideo.dto.FeedItemEntity

class FeedDiffUtil : DiffUtil.ItemCallback<FeedItemEntity>() {
    // item是否相等
    override fun areItemsTheSame(oldItem: FeedItemEntity, newItem: FeedItemEntity) = oldItem.id == newItem.id

    // item的内容是否相等(我们已经在实体类里重写equals方法了)
    override fun areContentsTheSame(oldItem: FeedItemEntity, newItem: FeedItemEntity) = oldItem == newItem
}