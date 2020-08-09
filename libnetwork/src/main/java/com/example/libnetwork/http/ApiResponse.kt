package com.example.libnetwork.http

data class ApiResponse<T>(var success: Boolean?,
                          val status: Int?,
                          var message: String?,
                          var data: T?) {
}