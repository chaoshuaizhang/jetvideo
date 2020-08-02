package com.example.jetvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jetvideo.util.NavGraphBuilder
import com.example.libnavannotation.ActivityDestination
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var navController: NavController

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 创建NottomNavitation时已经把item的id与page的id建立了关联
        navController.navigate(item.itemId)
        // 返回false，表示没有被选中，就不会被着色
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //
        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        NavGraphBuilder.buildNavGraph(navController)
        navView.setOnNavigationItemSelectedListener(this)
    }
}
