package com.example.jetvideo.ui.find

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jetvideo.R

class FindFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TAG","FindFragment")
        var view = LayoutInflater.from(context).inflate(R.layout.frag_find, container, false)
        return view
    }
}