package com.example.jetvideo.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.ArrayDeque

/*
* 默认的fragmentNavigator是用replace来进行frag的切换的，这样
* 会导致切换时frag进行频繁创建，所以重写下，改成hide-show
* */
@Navigator.Name("myFragNavigator")
class MyFragNavigator(val context: Context, val manager: FragmentManager, val continerId: Int)
    : FragmentNavigator(context, manager, continerId) {

    val TAG = "MyFragNavigator"


    @ExperimentalStdlibApi
    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        if (manager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already" + " saved its state")
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val ft = manager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        var currFragment = manager.primaryNavigationFragment
        if (currFragment != null) {
            ft.hide(currFragment)
        }
        val tag = destination.id
        var frag = manager.findFragmentByTag(tag.toString())
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = manager.fragmentFactory.instantiate(context.classLoader, destination.className)
            frag.arguments = args
            ft.add(continerId, frag, destination.id.toString())
        }

        // 如果不加这一行，会导致frag的覆盖
        ft.setPrimaryNavigationFragment(frag)
        var field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        var mBackStack: ArrayDeque<Int> = field.get(this) as ArrayDeque<Int>

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.last() == destId)
        val isAdded: Boolean
        if (initialNavigation) {
            isAdded = true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                manager.popBackStack(
                        generateBackStackName(mBackStack.size, mBackStack.last()!!),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            isAdded = false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            isAdded = true
        }
        if (navigatorExtras is Extras) {
            val extras = navigatorExtras as Extras?
            for ((key, value) in extras!!.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId)
            return destination
        } else {
            return null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }

}