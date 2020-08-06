package com.example.jetvideo.util

import com.example.jetvideo.App

class DimenUtil {
    companion object{
        fun dp2px(dp:Float):Float{
            return App.app.resources.displayMetrics.density * dp +0.5f
        }
    }
}