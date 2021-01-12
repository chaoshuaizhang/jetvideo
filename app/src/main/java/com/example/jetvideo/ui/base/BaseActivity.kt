package com.example.jetvideo.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initView()
        initData()
    }

    protected open fun setContentView() {
        setContentView(getLayoutId())
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected open fun initData() {}

}