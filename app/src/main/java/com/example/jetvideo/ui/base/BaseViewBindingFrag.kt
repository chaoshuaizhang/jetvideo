package com.example.jetvideo.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFrag<VB : ViewBinding> : BaseFragment() {

    lateinit var binding: VB

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = getViewBinding()
        return binding.root
    }

    abstract fun getViewBinding(): VB

}