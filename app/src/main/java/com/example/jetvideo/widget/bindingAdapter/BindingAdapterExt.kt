package com.example.jetvideo.widget.bindingAdapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.renderscript.*
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.example.jetvideo.R
import com.example.jetvideo.widget.IconTextView
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imgUrl")
fun bindImageView(view: ImageView?, url: String?) {
    view?.load(url)
}

@BindingAdapter("iconTvUrl")
fun setIconTvUrl(img: IconTextView, url: String?) {
    img.imageView.load(url) {
        placeholder(R.mipmap.ic_launcher)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@BindingAdapter("blurUrl", "radius")
fun setBlurView(view: ImageView, blurUrl: String, radius: Float) {
    view.load(blurUrl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            transformations(BlurTransformation(view.context, radius))
        } else {
            val loader = ImageLoader(view.context)
            val request = ImageRequest.Builder(view.context)
                    .data(blurUrl)
                    .target {
                        val bitmap = (it as BitmapDrawable).bitmap
                        var script: RenderScript? = null
                        var tmpInt: Allocation? = null
                        var tmpOut: Allocation? = null
                        var blur: ScriptIntrinsicBlur? = null
                        try {
                            script = RenderScript.create(view.context)
                            tmpInt = Allocation.createFromBitmap(script, bitmap)
                            tmpOut = Allocation.createTyped(script, tmpInt.type)
                            blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script)).apply {
                                setRadius(radius)
                                setInput(tmpInt)
                                forEach(tmpOut)
                            }
                            tmpOut.copyTo(bitmap)
                            view.setImageBitmap(bitmap)
                        } finally {
                            script?.destroy()
                            tmpInt?.destroy()
                            tmpOut?.destroy()
                            blur?.destroy()
                        }
                    }
                    .build()
            loader.enqueue(request)
        }
    }
}

/*
* 内容为空时，隐藏布局
* */
@BindingAdapter("visibility")
fun viewVisibility(view: View, str: String?) {
    if (str.isNullOrEmpty()) view.visibility = View.GONE
    else view.visibility = View.VISIBLE
}