package com.example.jetvideo

import android.app.Application

class App : Application() {

    lateinit var app:App

    override fun onCreate() {
        super.onCreate()
        app = this;
    }

    companion object{
        // 定义全局的APP实例
        lateinit var app: App
    }



}