package com.example.jetvideo.dto

import java.io.Serializable

data class Comment(
        var id: Int = 0,
        var itemId: Long = 0,
        var commentId: Long = 0,
        var userId: Long = 0,
        var commentType: Int = 0,
        var createTime: Long = 0,
        var commentCount: Int = 0,
        var likeCount: Int = 0,
        var commentText: String? = null,
        var imageUrl: String? = null,
        var videoUrl: String? = null,
        var width: Int = 0,
        var height: Int = 0,
        var hasLiked: Boolean = false,
        var author: AuthorEntity? = null,
        var ugc: UgcEntity? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (other == null || (other !is Comment)) return false
        val o = (other as? Comment) ?: return false
        return commentType == o.commentType &&
                commentCount == o.commentCount &&
                likeCount == o.likeCount &&
                commentText == o.commentText &&
                hasLiked == o.hasLiked
    }
}
