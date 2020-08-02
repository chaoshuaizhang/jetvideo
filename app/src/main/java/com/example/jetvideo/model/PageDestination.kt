package com.example.jetvideo.model

class PageDestination(
        val asStarter: Boolean,
        needLogin: Boolean,
        val className: String,
        val isActivity: Boolean,
        val id: Int,
        val routerUrl: String)