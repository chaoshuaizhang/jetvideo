package com.example.jetvideo.dto

import java.io.Serializable

const val TYPE_IMG = 1
const val TYPE_VIDEO = 2

data class FeedItemEntity(
        val activityIcon: Any?,
        val activityText: String?,
        val authorId: Int?,
        val cover: String?,
        val createTime: Long?,
        val duration: Int?,
        val feeds_text: String?,
        val height: Int?,
        val id: Int?,
        val itemId: Long?,
        val itemType: Int?,
        val topComment: Comment?,
        val url: String?,
        val width: Int?,
        val author: AuthorEntity?,
        val ugc: UgcEntity?,
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is FeedItemEntity) return false
        val o = (other as? FeedItemEntity) ?: return false
        return activityIcon == o.activityIcon &&
                activityText == o.activityText &&
                authorId == o.authorId &&
                cover == o.cover &&
                createTime == o.createTime &&
                duration == o.duration &&
                feeds_text == o.feeds_text &&
                id == o.id &&
                itemId == o.itemId &&
                itemType == o.itemType &&
                topComment == o.topComment &&
                url == o.url &&
                author == o.author &&
                ugc == o.ugc

    }


}
