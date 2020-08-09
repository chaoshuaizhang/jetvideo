package com.example.libcommon.entity

/*
* 用户信息实体类
* */
data class Author(val id:Int,
                  val userId:Long,
                  val name:String,
                  val avator:String,
                  val description:String,
                  val likeCount:Int,
                  val topCommentCount:Int,
                  val followCount:Int,
                  val followerCount:Int,
                  val qqOpenId:String,
                  val expires_time:Long,
                  val score:Int,
                  val historyCount:Int,
                  val commentCount:Int,
                  val favoriteCount:Int,
                  val feedCount:Int,
                  val hasFollow:Boolean
                  )