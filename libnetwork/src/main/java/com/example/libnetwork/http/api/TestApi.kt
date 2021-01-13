package com.example.libnetwork.http.api

import com.example.libnetwork.db.entity.WordDTO
import io.reactivex.rxjava3.core.Observable

interface TestApi {

    fun queryById(): Observable<List<WordDTO>>

}