package com.example.jetvideo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * 对于ViewBinding来说，这里需要换成
        * */
        setContentView(getLayoutId())
    }

    protected abstract fun getLayoutId(): Int

}