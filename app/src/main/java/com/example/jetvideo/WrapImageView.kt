package com.example.jetvideo

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView

/*
* 当宽 > 高时，以宽为基准，撑满，高按照比例进行缩放
* 当高 > 宽时，高度的最大值指定为屏幕的宽度
* */
class WrapImageView : ImageView {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            super(context, attributeSet, defStyleAttr){
    }

    private fun initSize(w: Int, h: Int, marginLeft: Int, max: Int) {
        var wTmp = w;
        var hTmp = h;
        if (w >= h) {
            if (w != max) {
                wTmp = max
            }

            // 得到宽度的缩放比例
            val ratio = w / max as Float
            hTmp /= ratio as Int
        } else {
            if (h > max) {
                hTmp = max
                val ratio = h / max as Float
                wTmp /= ratio as Int
            }
        }
        val params = ViewGroup.MarginLayoutParams(wTmp, hTmp)
        layoutParams = params
    }

}