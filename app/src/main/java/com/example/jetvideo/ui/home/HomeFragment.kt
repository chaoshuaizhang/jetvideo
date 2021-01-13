package com.example.jetvideo.ui.home

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetvideo.adapter.MyTestAdapter
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.ui.base.BaseViewBindingFrag
import com.example.libnetwork.db.entity.ApiResponse
import com.example.libnetwork.db.entity.WordDTO
import com.example.libnetwork.http.HttpClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

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

    override fun initData() {
        HttpClient.getRequest<ApiResponse<WordDTO>>("https://wanandroid.com/wxarticle/chapters/json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }
    }

}