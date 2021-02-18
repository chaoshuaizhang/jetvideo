package com.example.jetvideo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.jetvideo.R
import com.example.jetvideo.util.ScreenUtil
import com.example.jetvideo.widget.bindingAdapter.bindImageView
import com.example.jetvideo.widget.bindingAdapter.setBlurView
import com.example.libcommon.util.ext.resetParams
import kotlin.math.min

/*
* 视频播放组件
* 全局只有一个播放器，只有一个进度条，在播放时动态添加到playerView
* */
class PlayerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : ConstraintLayout(context, attrs, defStyle) {

    private var videoUrl: String? = null

    // 对应不同的页面
    private lateinit var category: String

    // 封面
    private val imgCover: WrapImageView

    // 高斯模糊背景
    private val imgBlur: AppCompatImageView

    // 播放按钮
    private val imgPlay: AppCompatImageView

    // 视频加载时的progressbar
    private val progressBar: ProgressBar

    init {
        inflate(context, R.layout.layout_player_view, this)
        imgCover = findViewById(R.id.img_cover)
        imgBlur = findViewById(R.id.img_blur)
        imgPlay = findViewById(R.id.img_video_pay)
        progressBar = findViewById(R.id.pg_video_load)
    }

    /*
    * 设置当前View的宽高，最大宽高均为屏幕宽度，需要适配以下View：
    * 当前View 宽度必定充满
    * 高斯模糊View
    * 封面View
    * 视频View与封面View大小一致
    * */
    private fun setLayoutSize(width: Int, height: Int, maxWidth: Int = ScreenUtil.w) {
        // FIXME: 2021/2/9/009 这个变量不需要生命
        val params = layoutParams.apply { this.width = maxWidth }
        if (width >= height) {
            // 此时宽度撑满屏幕，没有高斯模糊背景
            params.height = height * params.width / width
            imgCover.layoutParams.resetParams(params.width, params.height)
        } else {
            // 宽度按比例自适应，有高斯模糊背景
            val heightTmp = min(height, maxWidth)
            params.height = heightTmp
            imgCover.layoutParams.resetParams(heightTmp / height * width, params.height)
            // 高斯模糊
            imgBlur.layoutParams.resetParams(params.width, params.height)
        }
        // 当前View
        layoutParams.resetParams(params.width, params.height)
    }

    /*
    * 进行数据绑定，不用Databinding，用原生的方式
    * */
    // TODO: 2021/2/1/001 每个页面都有视频播放，所以需要player与页面关联起来，设置一个category参数
    fun bindData(wPx: Int, hPx: Int, coverUrl: String?, videoUrl: String?, category: String) {
        this.videoUrl = videoUrl
        this.category = category
        bindImageView(imgCover, coverUrl)
        if ((wPx < hPx).also { imgBlur.isVisible = it }) {
            // 宽度小于高度时，设置高斯模糊的背景
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                setBlurView(imgBlur, coverUrl, 10f)
            } else {
                // TODO: 2021/2/9/009
            }
        }
        setLayoutSize(wPx, hPx)
    }

}