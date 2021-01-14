package com.example.jetvideo.ui.home

import android.icu.util.TimeUnit
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetvideo.adapter.MyTestAdapter
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.ui.base.BaseViewBindingFrag
import com.example.libnetwork.db.entity.ApiResponse
import com.example.libnetwork.db.entity.WordDTO
import com.example.libnetwork.http.HttpClient
import com.example.libnetwork.http.MyCallback
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

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

        binding.myButton.setOnClickListener {
            HttpClient.getRequest<ApiResponse<WordDTO>>("https://wanandroid.com/wxarticle/chapters/json")
                .setConvertType(object : TypeToken<ApiResponse<WordDTO>>() {}.type)
                .enqueue()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("onResponseTAG---", "getRequestWithCb: ${Thread.currentThread().name}")
                    Toast.makeText(context, "${it.data.size}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun initData() {

    }

}