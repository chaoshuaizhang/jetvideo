package com.example.jetvideo.ui.publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetvideo.R
import com.example.libnavannotation.ActivityDestination
import com.example.libnavannotation.FragmentDestination

@ActivityDestination(routerUrl = "main/tab/publish")
class PublishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }
}
