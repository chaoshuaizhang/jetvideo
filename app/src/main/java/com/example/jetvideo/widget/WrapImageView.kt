package com.example.jetvideo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import coil.imageLoader
import coil.request.ImageRequest
import com.example.jetvideo.util.ScreenUtil
import com.example.libcommon.util.dp

class WrapImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : AppCompatImageView(context, attrs, defStyle) {

    /*
    * w > h 则宽度全撑满，高度自适应
    * h > w 则距离左边有margin，并且h的最大高度为屏幕最大宽度
    * */
    public fun bindData(width: Int = 0, height: Int = 0, marginLeft: Int = 20.dp, maxWidth: Int = ScreenUtil.w, maxHeight: Int = ScreenUtil.h, url: String) {
        val request = ImageRequest.Builder(context)
                .data(url)
                .target {
                    if (width <= 0 || height <= 0) {
                        handleDrawableResult(it.intrinsicWidth, it.intrinsicHeight, maxWidth, maxHeight, marginLeft)
                    } else {
                        // 已经预设了w和h，用预设的进行比例计算
                        handleDrawableResult(width, height, maxWidth, maxWidth, marginLeft)
                    }
                    setImageDrawable(it)
                }.build()
        context.imageLoader.enqueue(request)
    }

    private fun handleDrawableResult(w: Int, h: Int, maxWidth: Int, maxWidth1: Int, marginLeft: Int) {
        val finalW: Int
        val finalH: Int
        if (w >= h) {
            finalW = maxWidth
            finalH = (h / w.toFloat() * finalW).toInt()
        } else {
            finalH = maxHeight
            finalW = (w / h.toFloat() * finalH).toInt()
        }
        val params = ViewGroup.MarginLayoutParams(finalW, finalH)
        if (h > w) params.leftMargin = marginLeft
        layoutParams = params
    }
}