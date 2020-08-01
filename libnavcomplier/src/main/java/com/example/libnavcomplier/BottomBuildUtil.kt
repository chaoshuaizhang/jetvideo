package com.example.libnavcomplier

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.awt.Color
import java.lang.Exception

class BottomBuildUtil {

    companion object {

        var list = listOf<BottomTab>()
        val tabsTitle = arrayOf("首页", "分类", "", "购物车", "我的")
        val tabsIcon = arrayOf(0, 0, 0, 0, 0)
        val tabsEnable = arrayOf(true, true, true, true, true)
        val tabsSize = arrayOf(26, 26, 26, 26, 26)
        val tabsUrl = arrayOf(
                "main/tab/home",
                "main/tab/category",
                "main/tab/haha",
                "main/tab/cart",
                "main/tab/owner"
        )

        fun generateBottomTabJson() {
            var barObj = JSONObject()
            barObj.put("enableColor", "#333333")
            barObj.put("disenableColor", "#666666")
            var tabs = JSONArray(5)
            for (i in tabsTitle.indices) {
            }
        }
    }

    class BottomTabBar(val enableColor: String, val disableColor: String) {

        companion object {
            class Builder {

                lateinit var enableColor: String

                lateinit var disableColor: String

                fun build(): BottomTabBar {
                    if (!::enableColor.isInitialized)
                        throw Exception("没有设置选中时的颜色")
                    if (!::disableColor.isInitialized)
                        throw Exception("没有设置未选中时的颜色")


                    return BottomTabBar("", "")
                }

            }
        }
    }
    class BottomTab(title: String) {}
}