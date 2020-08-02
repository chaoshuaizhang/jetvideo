package com.example.jetvideo.util

import android.content.res.AssetManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.example.jetvideo.App
import com.example.jetvideo.model.PageDestination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class AppConfig {
    companion object {

        private lateinit var destinationMap: Map<String, PageDestination>

        fun parsePageJson(): Map<String, PageDestination> {
            if (::destinationMap.isInitialized) {
                return destinationMap
            }
            var stream: InputStream? = null
            var bufferedReader: BufferedReader? = null
            val sb: StringBuilder
            try {
                stream = getAssetManager().open("routers.json")
                bufferedReader = BufferedReader(InputStreamReader(stream))
                sb = StringBuilder()
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
                    bufferedReader.lines().forEach {
                        sb.append(it)
                    }
                else {
                    var readLines = bufferedReader.readLines()
                    readLines.forEach {
                        sb.append(it)
                    }
                }
            } finally {
                try {
                    stream?.close()
                } catch (e: Exception) {
                }
                try {
                    bufferedReader?.close()
                } catch (e: Exception) {
                }
            }
            destinationMap = JSON.parseObject(sb.toString(), object : TypeReference<HashMap<String, PageDestination>>(){}.type);
            return destinationMap

        }

        private fun getAssetManager(): AssetManager {
//            return AppGlobals.getAppInstance().assets
            return App.app.assets
        }
    }
}