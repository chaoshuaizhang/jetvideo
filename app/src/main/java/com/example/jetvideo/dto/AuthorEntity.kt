package com.example.jetvideo.dto

import java.io.Serializable

data class AuthorEntity(
        val avatar: String?,
        val commentCount: Int?,
        val description: String?,
        val expires_time: Long?,
        val favoriteCount: Int?,
        val feedCount: Int?,
        val followCount: Int?,
        val followerCount: Int?,
        val hasFollow: Boolean?,
        val historyCount: Int?,
        val id: Int?,
        val likeCount: Int?,
        val name: String?,
        val qqOpenId: String?,
        val score: Int?,
        val topCommentCount: Int?,
        val userId: Int?,
) : Serializable {
    fun tag(): String {
        likeCount?.let {
            return when {
                it > 1000 -> "好评如潮"
                it > 500 -> "潜力皮友"
                else -> "粉丝飙升"
            }
        }
        return ""
    }
}