package com.example.libnetwork.http

/*
* 用抽象类的原因是：需要获取到泛型类型
* */
abstract class MyCallback<T> {

    abstract fun onSuccess(t: T)

    abstract fun onError(e: Exception)
}