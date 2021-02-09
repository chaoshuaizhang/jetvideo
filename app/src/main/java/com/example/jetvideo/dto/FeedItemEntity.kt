package com.example.jetvideo.dto

import java.io.Serializable

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
) : Serializable
