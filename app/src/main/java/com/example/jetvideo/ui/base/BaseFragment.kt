package com.example.jetvideo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getContentView(inflater, container)
    }

    protected open fun getContentView(inflater: LayoutInflater, container: ViewGroup?) =
        inflater.inflate(getLayoutId(), container, false)

    protected open fun getLayoutId() = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    protected open fun initData() {}

}