package com.example.libcommon.entity

/*
* 完整的一个信息实体类（包括作者、评论、内容）
* */
data class Feed (
    var id: Int,
    var itemId: Long,
    var itemType: Int,
    var createTime: Long,
    var duration: Double,
    var feeds_text: String?,
    var authorId: Long,
    var activityIcon: String?,
    var activityText: String?,
    var width: Int,
    var height: Int,
    var url: String?,
    var cover: String?,
    var author: Author,
    val topComment:Comment,
    var ugc:Ugc
)