package com.example.libnetwork.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.libcommon.util.AppGlobal
import com.example.libnetwork.db.dao.CacheDao
import com.example.libnetwork.db.entity.Cache

@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {

    /*
    * 只有在这里声明了CacheDao，才会生成CacheDao_Impl文件
    * */
    abstract fun getCacheDao(): CacheDao

    companion object {

        @Volatile
        private var cacheDatabase: CacheDatabase? = null

        init {
            // 可以把db的初始化定义在这里，类似于饿汉式，但是参考了官方的示例，定义为DCL的单例
        }

        /*
        * 创建内存数据库，进程杀死后，数据丢失
        * */
        // Room.inMemoryDatabaseBuilder()
        private fun createDbInstance() = Room.databaseBuilder(AppGlobal.getAppInstance(),
                CacheDatabase::class.java, "jetvideo_cache.db")
                // 是否允许主线程做查询，默认是不允许
                .allowMainThreadQueries()
                // 设置数据库发生一些操作时进行的回调
                .addCallback(object : Callback() {
                    // onCreate
                    // onOpen
                    // onDestructiveMigration 破坏性迁移
                })
                // 设置线程池
                //.setQueryExecutor()
                // 设置类似SqliteOpenHelper
                //.openHelperFactory()
                // 设置日志模式
                //.setJournalMode()
                // 在版本升级过程中，出现异常时会删除所有数据
                .fallbackToDestructiveMigration()
                // 发生异常时，根据我们指定的数据库版本进行恢复
                .fallbackToDestructiveMigrationFrom(1, 2, 3)
                // 数据库降级时发生异常，则会删除所有数据进行重建数据库
                .fallbackToDestructiveMigrationOnDowngrade()
                // .addMigrations(getMigrations())
                .build()


        private fun getMigrations(): Migration {
            /*
            * 和greendao一样，在数据库升级时会清除所有数据，所以我们可以设置一些migrations，
            * 来在升级过程中进行执行，创建备份数据、恢复数据
            * */
            val migrations = object : Migration(1, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("")
                }
            }
            return migrations
        }

        fun getDbInstance(): CacheDatabase = cacheDatabase
                ?: synchronized(this) {
            cacheDatabase
                    ?: createDbInstance()
        }

    }

}