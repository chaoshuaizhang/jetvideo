package com.example.jetvideo.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator

class NavGraphBuilder {
    companion object{
        @RequiresApi(Build.VERSION_CODES.N)
        fun buildNavGraph(navController: NavController){
            var navigatorProvider = navController.navigatorProvider
            var fragmentNavigator = navigatorProvider.getNavigator(FragmentNavigator::class.java)
            var map = AppConfig.parsePageJson()
        }
    }
}