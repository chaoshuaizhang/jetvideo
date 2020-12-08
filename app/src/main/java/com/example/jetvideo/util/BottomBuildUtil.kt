package com.example.jetvideo.util

import com.example.jetvideo.R

class BottomBuildUtil {

    companion object {

        private lateinit var bottomBar: BottomTabBar

        fun generateBottomBar(): BottomTabBar {
            if (!::bottomBar.isInitialized) {
                // 根据需要，创建一系列的bar
                bottomBar = initBottomTabBar()
            }
            return bottomBar
        }

        private fun initBottomTabBar(): BottomTabBar {

            var tabBar = BottomTabBar.Companion.Builder()
                    .enableColor("#333333")
                    .disableColor("#666666")
                    .addTab(BottomTab(true, "首页", R.drawable.icon_tab_home, 24f, "main/tab/home"))
                    .addTab(BottomTab(true, "沙发", R.drawable.icon_tab_soft, 24f, "main/tab/soft"))
                    .addTab(BottomTab(true, "", R.drawable.icon_tab_publish, 30f, "main/tab/publish"))
                    .addTab(BottomTab(true, "发现", R.drawable.icon_tab_find, 24f, "main/tab/find"))
                    .addTab(BottomTab(true, "我的", R.drawable.icon_tab_owner, 24f, "main/tab/owner"))
                    .build()
            return tabBar
        }
    }


}

class BottomTabBar(val enableColor: String,
                   val disableColor: String,
                   val tabs: MutableList<BottomTab>) {

    val selectedTab = 0

    companion object {
        class Builder {

            lateinit var enColor: String

            lateinit var disColor: String

            lateinit var tabs: MutableList<BottomTab>

            fun enableColor(enableColor: String): Builder {
                enColor = enableColor
                return this
            }

            fun disableColor(disableColor: String): Builder {
                disColor = disableColor
                return this
            }

            fun addTab(tab: BottomTab): Builder {
                if (!::tabs.isInitialized) {
                    tabs = mutableListOf()
                }
                tabs.add(tab)
                return this
            }

            fun build(): BottomTabBar {
                if (!::enColor.isInitialized)
                    throw Exception("没有设置选中时的颜色")
                if (!::disColor.isInitialized)
                    throw Exception("没有设置未选中时的颜色")
                if (!::tabs.isInitialized)
                    throw Exception("没有设置Tab页签")

                return BottomTabBar(enColor, disColor, tabs)
            }

        }
    }
}

class BottomTab(val enable: Boolean,
                val title: String,
                val iconRes: Int,
                val size: Float,
                val pageUrl: String)
