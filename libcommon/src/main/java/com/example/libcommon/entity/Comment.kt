package com.example.libcommon.entity

/*
* 评论实体类
* */
data class Comment (
    var id: Int,
    var itemId: Long,
    var commentId: Long,
    var userId: Long,
    var commentType: Int,
    var createTime: Long,
    var commentCount: Int,
    var likeCount: Int,
    var commentText: String?,
    var imageUrl: String?,
    var videoUrl: String?,
    var width: Int,
    var height: Int,
    var hasLiked: Boolean,
    var author: Author?,
    var ugc: Ugc?
    )