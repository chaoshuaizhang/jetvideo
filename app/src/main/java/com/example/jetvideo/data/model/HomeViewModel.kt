package com.example.jetvideo.data.model

import androidx.lifecycle.MutableLiveData
import com.example.jetvideo.data.repository.HomeRepository

class HomeViewModel : BaseViewModel() {

    val homeRepository = HomeRepository()

    val feedList = MutableLiveData<String>()

    fun loadFeeds() {

    }

}