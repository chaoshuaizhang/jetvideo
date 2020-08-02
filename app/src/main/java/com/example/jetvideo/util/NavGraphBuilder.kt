package com.example.jetvideo.util

import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.example.jetvideo.App

class NavGraphBuilder {
    companion object {
        fun buildNavGraph(navController: NavController, activity: FragmentActivity, containerId: Int) {
            var navigatorProvider = navController.navigatorProvider
            // 使用navigator来创建destination，会导致frag是以replace形式加载的
            var fragmentNavigator = MyFragNavigator(activity, activity.supportFragmentManager, containerId)
            // 注意，需要把我们自己创建的navigator加到provider中
            navigatorProvider.addNavigator(fragmentNavigator)
            var activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
            var map = AppConfig.parsePageJson()
            val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))
            map.values.forEach {
                if (it.isActivity) {
                    var destination = activityNavigator.createDestination()
                    destination.setComponentName(ComponentName(App.app.packageName, it.className))
                    destination.addDeepLink(it.routerUrl)
                    destination.id = it.id
                    navGraph.addDestination(destination)
                } else {
                    var destination = fragmentNavigator.createDestination()
                    destination.className = it.className
                    destination.addDeepLink(it.routerUrl)
                    destination.id = it.id
                    navGraph.addDestination(destination)
                }
                if (it.asStarter) {
                    navGraph.startDestination = it.id
                }
            }
            navController.graph = navGraph
        }
    }
}