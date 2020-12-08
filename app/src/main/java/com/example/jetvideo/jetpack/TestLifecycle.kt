package com.example.jetvideo.jetpack

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class TestLifecycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun ON_CREATE(){
        Log.d("lifecycle", "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun ON_RESUME(){
        Log.d("lifecycle", "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun ON_STOP(){
        Log.d("lifecycle", "ON_STOP")
    }

}
