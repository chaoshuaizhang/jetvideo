package com.example.libnetwork.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cache")
data class Cache(
    @PrimaryKey val key: String,
    val data: ByteArray,
    val nameStr: String = "1122", val nameLong: Long = 12345
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cache

        if (key != other.key) return false
        if (!data.contentEquals(other.data)) return false
        if (nameStr != other.nameStr) return false
        if (nameLong != other.nameLong) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + nameStr.hashCode()
        result = 31 * result + nameLong.hashCode()
        return result
    }
}
