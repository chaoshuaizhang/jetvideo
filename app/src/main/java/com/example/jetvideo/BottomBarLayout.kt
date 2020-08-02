package com.example.jetvideo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import androidx.core.view.children
import com.example.jetvideo.util.AppConfig
import com.example.jetvideo.util.BottomBuildUtil
import com.example.jetvideo.util.DimenUtil
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class BottomBarLayout : BottomNavigationView {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    @SuppressLint("RestrictedApi")
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr) {

        var bottomBar = BottomBuildUtil.generateBottomBar()

        var colors = intArrayOf(Color.parseColor(bottomBar.enableColor), Color.parseColor(bottomBar.disableColor))
        var states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        // 代码设置状态转换，类似在xml中设置selector，选中时颜色、为选中时颜色
        val colorStateList = ColorStateList(states, colors)

        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        // 不管选不选中，都显示title文字
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        var tabs = bottomBar.tabs
        // 默认选中的tab 位置
        selectedItemId = bottomBar.selectedTab
        var index = 0
        tabs.forEach {
            if (it.enable) {
                // 得到对应pageDestination的id，与tabItem一一对应
                var parsePageJson = AppConfig.parsePageJson()
                var pageDestination = parsePageJson[it.pageUrl]
                var id = AppConfig.parsePageJson()[it.pageUrl]!!.id.toInt()
                menu.add(0, id, index++, it.title).setIcon(it.iconRes)
            }
        }

        var view = getChildAt(0) as BottomNavigationMenuView
        // 为什么要单独设置icon的size？
        // 因为每次执行add操作后，都会重新添加各个item，会导致之前设置的size无效
        for (i in tabs.indices) {
            val v = view.getChildAt(i) as BottomNavigationItemView
            v.setIconSize(DimenUtil.dx2px(tabs[i].size).toInt())
            if (TextUtils.isEmpty(tabs[i].title)) {
                // 点击时 禁止抖动
                v.setShifting(false)
            }
        }
    }
}