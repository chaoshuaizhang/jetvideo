package com.example.jetvideo

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.example.jetvideo.model.PageDestination
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.HashMap

open class Ktest {
    companion object{

        init {
            println("----------------")
        }

        fun testMap(){

            var k = "22222"
            k = k ?: "11111"

            println(k)

            var map = Jtest.getMap()
            map.forEach { t, u ->
                print("$t     ")
                println(u)
            }
        }

        fun testReflect(){

            var declaredMethod = Class.forName("com.example.jetvideo.Jtest")::class
                    .java.getDeclaredMethod("getMap")
            var invoke = declaredMethod.invoke(Any(),Any())
            println(invoke)
        }

        fun testFile(){
            val fis = FileInputStream("E:\\myproject\\JetVideo\\build.gradle")
            val inputStream = InputStreamReader(fis)
            val bufferedReader = BufferedReader(inputStream)
            bufferedReader.lines().forEach {
                println(it)
            }
        }

        fun testIntegerReflect(){
            val value:Int = 8
            var javaClass = value.javaClass
            val field = javaClass.javaClass.getDeclaredField("TYPE")
            val clazz = field.get(null) as Class<*>
            if (clazz.isPrimitive()) {
                println("-------------")
            }
        }

    }
}