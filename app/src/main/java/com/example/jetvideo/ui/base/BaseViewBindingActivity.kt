package com.example.jetvideo.ui.base

import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseActivity() {

    lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    override fun setContentView() {
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun getLayoutId() = 0


}