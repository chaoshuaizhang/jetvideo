//package com.example.libnetwork.http.api.impl
//
//import com.example.libnetwork.db.entity.WordDTO
//import com.example.libnetwork.http.A
//import com.example.libnetwork.http.api.TestApi
//import com.example.libnetwork.http.BaseRequest
//import com.example.libnetwork.http.HttpClient
//import io.reactivex.rxjava3.core.Observable
//import okhttp3.*
//import java.io.IOException
//import java.lang.reflect.Type
//
//class TestApiImpl : TestApi {
//
//    val request = BaseRequest()
//
//    override fun queryById(): Observable<List<WordDTO>> {
//        return getCommonObservable(List<WordDTO>::class.java.componentType)
//    }
//
//    private fun <T> getCommonObservable(type: Type): Observable<T> {
//        return Observable.create {
//            request.getCall().enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//
//                }
//            })
//        }
//    }
//
//    private fun onResponse(call: Call, response: Response) {
//
//    }
//
//    private fun onFailure(call: Call, e: IOException) {
//
//    }
//
//    fun wrapperResponse() {
//
//    }
//
//
//}
//
//fun main() {
//    // use
//    val testApi = TestApiImpl()
//    testApi.queryById().subscribe {
//        // result
//        println(it)
//    }
//}