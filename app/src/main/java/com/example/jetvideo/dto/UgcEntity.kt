package com.example.jetvideo.dto

import java.io.Serializable

data class UgcEntity(
        val commentCount: Int?,
        val hasDissed: Boolean?,
        val hasFavorite: Boolean?,
        val hasLiked: Boolean?,
        val hasdiss: Boolean?,
        val likeCount: Int?,
        val shareCount: Int?,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (other == null || (other !is UgcEntity)) return false
        val o = (other as? UgcEntity) ?: return false
        return commentCount == o.commentCount &&
                hasDissed == o.hasDissed &&
                hasFavorite == o.hasFavorite &&
                hasdiss == o.hasdiss &&
                likeCount == o.likeCount &&
                shareCount == o.shareCount &&
                hasLiked == o.hasLiked
    }
}