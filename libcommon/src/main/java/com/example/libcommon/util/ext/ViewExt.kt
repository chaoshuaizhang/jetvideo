package com.example.libcommon.util.ext

import android.view.ViewGroup

fun ViewGroup.LayoutParams.resetParams(w: Int? = null, h: Int? = null) {
    w?.let { width = it }
    h?.let { height = it }
}