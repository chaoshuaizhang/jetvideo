package com.example.jetvideo.ui

import androidx.navigation.fragment.NavHostFragment
import com.example.jetvideo.R
import com.example.jetvideo.ui.base.BaseViewBindingActivity
import com.example.jetvideo.databinding.ActivityMainBinding
import com.example.jetvideo.util.HideShowFragNavigator

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        val navController = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
        navController.navigatorProvider.addNavigator("hideShowFragNavigator",
            HideShowFragNavigator(this, supportFragmentManager, R.id.container))
        navController.setGraph(R.navigation.nav_graph)
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.navigation_soft -> navController.navigate(R.id.softFragment)
                R.id.navigation_find -> navController.navigate(R.id.findFragment)
                R.id.navigation_mine -> navController.navigate(R.id.ownerFragment)
            }
            true
        }
    }

}
