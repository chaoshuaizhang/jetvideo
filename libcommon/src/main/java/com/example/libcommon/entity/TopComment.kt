package com.example.libcommon.entity

data class TopComment(
        val commentCount: Int,
        val commentId: Long,
        val commentText: String,
        val commentType: Int,
        val createTime: Int,
        val hasLiked: Boolean,
        val height: Int,
        val id: Int,
        val imageUrl: Any,
        val itemId: Long,
        val likeCount: Int,
        val userId: Int,
        val videoUrl: Any,
        val width: Int,
        val author: Author,
        val ugc: Ugc
)