package com.example.libcommon.util.ext

import android.widget.Toast
import com.example.libcommon.AppGlobal

fun toast(msg: String) {
    Toast.makeText(AppGlobal.app.applicationContext, msg, Toast.LENGTH_SHORT).show()
}

fun toastLong(msg: String) {
    Toast.makeText(AppGlobal.app.applicationContext, msg, Toast.LENGTH_LONG).show()
}