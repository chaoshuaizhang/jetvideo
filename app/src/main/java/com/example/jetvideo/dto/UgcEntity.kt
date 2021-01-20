package com.example.jetvideo.dto

import java.io.Serializable

data class UgcEntity(
    val commentCount: Int?,
    val hasDissed: Boolean?,
    val hasFavorite: Boolean?,
    val hasLiked: Boolean?,
    val hasdiss: Boolean?,
    val likeCount: Int?,
    val shareCount: Int?
) : Serializable