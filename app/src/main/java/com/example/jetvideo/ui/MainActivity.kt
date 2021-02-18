package com.example.jetvideo.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.jetvideo.R
import com.example.jetvideo.ui.base.BaseViewBindingActivity
import com.example.jetvideo.databinding.ActivityMainBinding
import com.example.jetvideo.util.HideShowFragNavigator
import com.example.libcommon.util.ext.logd
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        val nav = supportFragmentManager.findFragmentById(R.id.container)
        var navController: NavController
        try {
            if(nav is NavHostFragment){
                val navC = (nav as NavHostFragment).navController
                navController = navC
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
            }else{
                logd("转换异常  ${nav!!::class.simpleName}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
