package com.example.libnetwork.db.dao

import androidx.room.*
import com.example.libnetwork.db.entity.Cache

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache):Long

    @Query(value = "select * from cache where `key` = :key")
    fun query(key: String): Cache

    @Delete
    fun delete(cache: Cache)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache)
}