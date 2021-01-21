package com.example.jetvideo.ui.home

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONObject
import com.example.jetvideo.R
import com.example.jetvideo.adapter.MyTestAdapter
import com.example.jetvideo.data.model.HomeViewModel
import com.example.jetvideo.databinding.FragHomeBinding
import com.example.jetvideo.dto.FeedEntity
import com.example.jetvideo.ui.base.BaseDataBindingFrag
import com.example.jetvideo.ui.base.BaseViewBindingFrag
import com.example.libcommon.util.typeToken
import com.example.libnetwork.db.CacheDatabase
import com.example.libnetwork.db.entity.Cache
import com.example.libnetwork.db.entity.WordDTO
import com.example.libnetwork.http.HttpClient
import com.example.libnetwork.http.NET_CACHE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.flow
import okhttp3.*
import java.util.*
import kotlin.random.Random

class HomeFragment : BaseDataBindingFrag<FragHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()

    override fun getLayoutId() = R.layout.frag_home

    override fun initView() {
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            val list = mutableListOf<String>()
            for (i in 0 until 30) {
                list.add("$i")
            }
            adapter = MyTestAdapter(context, list)
        }

        viewModel.feedList.observe(this) {

        }
        binding.myButton.setOnClickListener {
            viewModel.loadFeeds()
            HttpClient.getPPJokeRequest<FeedEntity>("feeds/queryHotFeedsList")
                    .addQuery("feedId", 0)
                    .addQuery("feedType", "video")
                    .addQuery("pageCount", 10)
                    .setConvertType(object : TypeToken<FeedEntity>() {}.type)
                    .enqueue()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("getPPJokeRequestTAG", "initView: ")
                        binding.feed = it.data[0]
                        Toast.makeText(activity, "${it.data[0].feeds_text}", Toast.LENGTH_SHORT).show()
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    override fun initData() {

    }
}

class MyTv(context: Context) : AppCompatTextView(context){
    override fun getCompoundDrawablePadding(): Int {
        return super.getCompoundDrawablePadding()
    }

    override fun getCompoundPaddingBottom(): Int {
        return super.getCompoundPaddingBottom()
    }
}