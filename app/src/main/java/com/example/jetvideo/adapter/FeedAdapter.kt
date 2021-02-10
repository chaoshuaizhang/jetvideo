package com.example.jetvideo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jetvideo.adapter.diffutil.FeedDiffUtil
import com.example.jetvideo.databinding.LayoutFeedTypeImageBinding
import com.example.jetvideo.databinding.LayoutFeedTypeVideoBinding
import com.example.jetvideo.dto.FeedItemEntity
import com.example.jetvideo.dto.TYPE_IMG
import com.example.jetvideo.dto.TYPE_VIDEO

typealias FeedVH = FeedAdapter.FeedViewHolder

class FeedAdapter(context: Context, val pageTag: String) : PagedListAdapter<FeedItemEntity, FeedVH>(FeedDiffUtil()) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_IMG -> FeedVH(LayoutFeedTypeImageBinding.inflate(inflater))
        TYPE_VIDEO -> FeedVH(LayoutFeedTypeVideoBinding.inflate(inflater))
        else -> throw Exception("can not parse the type [$viewType]")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.itemType ?: throw Exception("操作异常")
    }

    override fun onBindViewHolder(holder: FeedVH, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    inner class FeedViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(entity: FeedItemEntity) {
            when (binding) {
                is LayoutFeedTypeImageBinding -> {
                    binding.feed = entity
                    binding.imageContent.bindData(
                            entity.width ?: 0, entity.height ?: 0,
                            url = entity.cover)
                }
                is LayoutFeedTypeVideoBinding -> {
                    binding.feed = entity
                    binding.playerView.bindData(entity.width ?: 0, entity.height ?: 0,
                            entity.cover, entity.url, pageTag)
                }
            }
        }
    }

}