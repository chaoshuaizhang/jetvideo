package com.example.jetvideo.ui.soft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jetvideo.R
import com.example.libnavannotation.FragmentDestination

@FragmentDestination(routerUrl = "main/tab/soft")
class SoftFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.frag_soft, container, false)
    }
}