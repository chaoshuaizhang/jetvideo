package com.example.jetvideo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class ImageViewBindAdapter {
    companion object {
        /*
        * requireAll=true表示，只有在xml中把所有的属性都写上，才会调到这个adapter中
        * requireAll=false表示，只要有其中一个就行
        * */
        @BindingAdapter(value = ["imageUrl", "isCircle"], requireAll = true)
        fun bindImageView(img: ImageView, imgUrl: String, isCircle: Boolean) {
            val builder = Glide.with(img).load(imgUrl)
            isCircle.let {
                builder.transform(CircleCrop())
            }
            builder.into(img)
            if (img.layoutParams != null && img.layoutParams.width > 0 && img.layoutParams.height > 0) {
                // 防止图片自身过大
                builder.override(img.layoutParams.width, img.layoutParams.height)
            }
        }
    }
}