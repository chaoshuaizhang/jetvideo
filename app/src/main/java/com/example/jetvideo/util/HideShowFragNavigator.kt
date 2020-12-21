package com.example.jetvideo.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.example.libcommon.util.TAG
import com.google.android.material.transition.platform.MaterialSharedAxis
import java.util.*
import kotlin.collections.ArrayDeque

@Navigator.Name("hideShowFragNavigator")
class HideShowFragNavigator(val context: Context, val manager: FragmentManager, val container: Int)
    : FragmentNavigator(context, manager, container) {

    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?): NavDestination? {
        if (manager.isStateSaved) {
            Log.i(TAG(), "Ignoring navigate() call: FragmentManager has already saved its state")
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val transaction = manager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            transaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        // -----start-----replace转为hide-show的逻辑
        val primaryFrag = manager.primaryNavigationFragment
        var showFrag = manager.findFragmentByTag(destination.id.toString())
        if (primaryFrag == null) {
            // 加载第一个frag
            showFrag = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(container, showFrag, destination.id.toString())
        } else if (showFrag == primaryFrag)
        // 重复点击显示的fragment，不做处理
            return null
        else {
            transaction.hide(primaryFrag)
            if (showFrag == null) {
                showFrag = manager.fragmentFactory.instantiate(context.classLoader, className)
                transaction.add(container, showFrag, destination.id.toString())
            } else transaction.show(showFrag)
        }
        showFrag.enterTransition = createTransition()
        transaction.setPrimaryNavigationFragment(showFrag)
        @IdRes val destId = destination.id
        val mBackStackField = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        mBackStackField.isAccessible = true
        val mBackStack = mBackStackField.get(this) as java.util.ArrayDeque<Int>
        // -----end-----
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
            && navOptions.shouldLaunchSingleTop()
            && mBackStack.peekLast() == destId)

        val isAdded: Boolean
        isAdded = when {
            initialNavigation -> {
                true
            }
            isSingleTopReplacement -> {
                // Single Top means we only want one instance on the back stack
                if (mBackStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    manager.popBackStack(
                        generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.addToBackStack(generateBackStackName(mBackStack.size, destId))
                }
                false
            }
            else -> {
                transaction.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                true
            }
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                transaction.addSharedElement(key, value)
            }
        }
        transaction.setReorderingAllowed(true)
        transaction.commit()
        // The commit succeeded, update our view of the world
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String? {
        return "$backStackIndex-$destId"
    }

    private fun createTransition(): MaterialSharedAxis? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MaterialSharedAxis(MaterialSharedAxis.Z, true)
        } else null
    }

}