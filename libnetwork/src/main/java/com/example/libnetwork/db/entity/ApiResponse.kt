package com.example.libnetwork.db.entity

data class ApiResponse<T>(
    val status: Int?,
    val success: Boolean?,
    val message: String?,
    val data: T?
)
