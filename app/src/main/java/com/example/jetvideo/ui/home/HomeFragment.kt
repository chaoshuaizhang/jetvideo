package com.example.jetvideo.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetvideo.adapter.MyTestAdapter
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.ui.base.BaseViewBindingFrag

class HomeFragment : BaseViewBindingFrag<FragHomeBinding>() {

    override fun getViewBinding() = FragHomeBinding.inflate(layoutInflater)

    override fun initView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            val list = mutableListOf<String>()
            for (i in 0 until 30) {
                list.add("$i")
            }
            adapter = MyTestAdapter(context, list)
        }
    }
}