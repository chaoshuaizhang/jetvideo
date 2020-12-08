package com.example.libcommon.util

import android.text.TextUtils

class StringUtils {

    companion object{
        fun convertUgcLikeCount(n:Int):String?{
            if(n == 0){
                return null
            }
            if(n > 10000){
                return "${n / 10000}万"
            }
            return null
        }
    }

}