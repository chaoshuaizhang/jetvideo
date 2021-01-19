package com.example.libnetwork.db

import androidx.room.*
import com.example.libcommon.AppGlobal
import com.example.libnetwork.LongStrConverter
import com.example.libnetwork.db.entity.Cache

@Database(entities = [Cache::class], version = 1)
@TypeConverters(LongStrConverter::class)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        val cacheDbInstance by lazy {
            Room.databaseBuilder(AppGlobal.app, CacheDatabase::class.java, "cache.db")
                    // 默认不支持主线程查询
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigrationFrom()
                    .build()
        }
    }

    /*
    * 这里也是一个单例
    * */
    abstract fun cacheDao(): CacheDao
}