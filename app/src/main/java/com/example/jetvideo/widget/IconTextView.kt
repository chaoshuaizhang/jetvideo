package com.example.jetvideo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.jetvideo.R

/*
* 只支持图文混排，单独的文字、图片不支持
* */
class IconTextView @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attrs, defStyle) {

    var iconRes = 0
    var iconUrl: String? = null
    var tvRes = 0
    var tvStr: String? = null
    var iconPadding = 0
    var ivLayout = 0
    lateinit var icon: ImageView
    lateinit var text: TextView

    init {
        val styleRes = context.obtainStyledAttributes(attrs, R.styleable.IconTextView)
        iconRes = styleRes.getResourceId(R.styleable.IconTextView_icon, 0)
        iconUrl = styleRes.getString(R.styleable.IconTextView_iconUrl)
        tvRes = styleRes.getResourceId(R.styleable.IconTextView_text, 0)
        tvStr = styleRes.getString(R.styleable.IconTextView_text)
        iconPadding = styleRes.getDimensionPixelOffset(R.styleable.IconTextView_iconPadding, 5)
        ivLayout = styleRes.getInteger(R.styleable.IconTextView_ivlayout, 1)
        styleRes.recycle()
        gravity = Gravity.CENTER_VERTICAL
        layoutContent()
    }

    private fun layoutContent() {
        /*
        * name="tv_rtl" value="1"
        * name="tv_ltr" value="2"
        * name="tv_ttb" value="3"
        * name="tv_btt" value="4"
        * */
        if ((iconRes != 0 || iconUrl != null) && (tvRes != 0 || tvStr != null)) {
            icon = ImageView(context, attrs)
            text = TextView(context, attrs)
            val marginParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            when (ivLayout) {
                1 -> {
                    marginParams.rightMargin = iconPadding
                    icon.layoutParams = marginParams
                }
                2 -> {
                    marginParams.rightMargin = iconPadding
                    text.layoutParams = marginParams
                }
                3 -> {
                    marginParams.bottomMargin = iconPadding
                    text.layoutParams = marginParams
                }
                4 -> {
                    marginParams.bottomMargin = iconPadding
                    icon.layoutParams = marginParams
                }
            }
            addIcon(icon)
            addTextView(text)
        }
    }


    private fun addIcon(img: ImageView) {
        if (iconRes != 0) img.setImageResource(iconRes)
        addView(img)
    }

    private fun addTextView(textView: TextView) {
        if (tvRes != 0) textView.setText(tvRes)
        else textView.text = tvStr
        textView.includeFontPadding = false
        addView(textView)
    }

    companion object {

    }

}
@BindingAdapter("iconTvUrl")
fun setIconTvUrl(img: IconTextView, url: String?) {
    Log.d("setIconTvUrlTAG", "setIconTvUrl: $url")
}