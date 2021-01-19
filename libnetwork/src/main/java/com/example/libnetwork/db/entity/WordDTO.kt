package com.example.libnetwork.db.entity

import java.io.Serializable

data class WordDTO(val name: String, val courseId: Int, val nameStr: String = "1122", val nameLong: Long = 12345) : Serializable
