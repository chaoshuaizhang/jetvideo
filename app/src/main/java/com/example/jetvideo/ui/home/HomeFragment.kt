package com.example.jetvideo.ui.home

import android.icu.util.TimeUnit
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.example.jetvideo.adapter.MyTestAdapter
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.ui.base.BaseViewBindingFrag
import com.example.libnetwork.db.entity.ApiResponse
import com.example.libnetwork.db.entity.WordDTO
import com.example.libnetwork.http.HttpClient
import com.example.libnetwork.http.MyCallback
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import okhttp3.RequestBody.Companion.toRequestBody

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
            HttpClient.getWanAndroidRequest<List<WordDTO>>("wxarticle/chapters/json")
                .setConvertType(object : TypeToken<List<WordDTO>>() {}.type)
                .enqueue()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(context, "${it.size}      ${it.get(0).name}", Toast.LENGTH_SHORT).show()
                }, {
                    it.printStackTrace()
                })
        }
    }

    override fun initData() {

    }
}