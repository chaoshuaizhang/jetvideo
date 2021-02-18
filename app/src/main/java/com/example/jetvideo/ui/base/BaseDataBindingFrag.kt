package com.example.jetvideo.ui.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDataBindingFrag<DB : ViewDataBinding> : BaseFragment() {

    lateinit var binding: DB

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = getDataBinding(inflater, container)
        return binding.root
    }

    /*
    * 对于Fragment的rootview不是xml布局，而是一个自定义View时，此i
    * */
    open fun getDataBinding(inflater: LayoutInflater? = null, container: ViewGroup? = null): DB {
        inflater ?: throw Exception("inflater can not be null")
        return DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
    }

}