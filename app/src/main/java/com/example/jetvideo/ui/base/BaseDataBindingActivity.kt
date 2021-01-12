package com.example.jetvideo.ui.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDataBindingActivity<DB : ViewDataBinding> : BaseActivity() {

    lateinit var binding: DB

    override fun setContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

}