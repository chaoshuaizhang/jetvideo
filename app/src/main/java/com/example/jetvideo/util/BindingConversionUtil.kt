package com.example.jetvideo.util

import androidx.databinding.BindingConversion
import com.example.jetvideo.R

@BindingConversion
fun convertBool2Int(liked: Boolean): Int {
    return if (liked) return R.drawable.icon_cell_liked
    else R.drawable.icon_cell_like
}

@BindingConversion
fun convertBool2String(liked: Boolean): String {
    return if (liked) return "icon_cell_liked"
    else "icon_cell_like"
}


@BindingConversion
fun convertBool2String2(liked: Boolean): String {
    return if (liked) return "icon_cell_liked2"
    else "icon_cell_like2"
}