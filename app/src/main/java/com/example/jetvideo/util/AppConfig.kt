package com.example.jetvideo.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jetvideo.App
import com.example.jetvideo.model.PageDestination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class AppConfig {
    companion object {

        private lateinit var destinationMap: Map<String, PageDestination>

        @RequiresApi(Build.VERSION_CODES.N)
        fun parsePageJson(): Map<String, PageDestination> {
            if(::destinationMap.isInitialized){
                return destinationMap
            }
            var stream: InputStream? = null
            var bufferedReader: BufferedReader? = null
            val sb: StringBuilder
            try {
                stream = getAssetManager().open("routers.json")
                bufferedReader = BufferedReader(InputStreamReader(stream))
                sb = StringBuilder()
                bufferedReader.lines().forEach {
                    sb.append(it)
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
            destinationMap = JSON.parseObject(sb.toString(), HashMap<String, PageDestination>()::class.java);
            return destinationMap

        }

        fun parseTabBarJson() {

        }

        private fun getAssetManager(): AssetManager {
            return AppGlobals.getAppInstance().assets
        }
    }
}