package com.example.libnetwork.db

import androidx.room.*
import com.example.libnetwork.db.entity.Cache

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache)

    @Query("select * from cache where `key` = :key")
    fun query(key: String): Cache

    @Delete(entity = Cache::class)
    fun delete(key: String)

    @Delete
    fun delete(cache: Cache)

    @Update
    fun update(cache: Cache)

}