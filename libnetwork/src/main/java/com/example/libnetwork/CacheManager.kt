package com.example.libnetwork

import com.example.libnetwork.db.CacheDatabase
import com.example.libnetwork.db.entity.Cache
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {

    fun <T> cache(key: String, v: T) {

        ByteArrayOutputStream().use { baas ->
            ObjectOutputStream(baas).use { oos ->
                oos.writeObject(v)
                oos.flush()
                CacheDatabase.cacheDbInstance.cacheDao().insert(Cache(key, baas.toByteArray()))
            }
        }
    }

    fun <T> queryCache(key: String): T? {
        val cache = CacheDatabase.cacheDbInstance.cacheDao().query(key)
        if (cache == null) return null
        val bais = ByteArrayInputStream(cache.data)
        val ois = ObjectInputStream(bais)
        return ois.readObject() as? T
    }

}