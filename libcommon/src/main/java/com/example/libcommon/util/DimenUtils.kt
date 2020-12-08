package com.example.libcommon.util

class DimenUtils {
    companion object {

        fun dp2px(dp: Int): Int {
            return with(AppGlobalsKt.getAppInstance().resources.displayMetrics) {
                (density * dp + 0.5) as Int
            }
        }

        fun px2dp(px: Int): Int {
            return with(AppGlobalsKt.getAppInstance().resources.displayMetrics) {
                (px / density + 0.5) as Int
            }
        }

        fun getScreenWidth(): Int {
            return with(AppGlobalsKt.getAppInstance().resources.displayMetrics) {
                widthPixels
            }
        }

        fun getScreenHeight(): Int {
            return with(AppGlobalsKt.getAppInstance().resources.displayMetrics) {
                heightPixels
            }
        }
    }
}