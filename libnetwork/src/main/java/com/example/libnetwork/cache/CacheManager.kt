package com.example.libnetwork.cache

import com.example.libnetwork.db.entity.Cache
import com.example.libnetwork.db.CacheDatabase
import java.io.*

class CacheManager {

    companion object {

        /*
        * 存储
        * */
        fun <T> save(key: String, data: T?) {
            val cache = Cache(key, data2ByteArray(data))
            CacheDatabase.getDbInstance().getCacheDao().insert(cache)
        }

        /*
        * 读取
        * */
        fun <T> get(key: String):T?{
            key.let {
                val cache = CacheDatabase.getDbInstance().getCacheDao().query(key)
                cache?.let {
                    return byteArray2Data<T>(cache.data)
                }
                return null
            }
        }

        fun del(){
        }

        private fun <T> data2ByteArray(data: T?): ByteArray {
            val baos = ByteArrayOutputStream()
            baos.use { baas ->
                val oos = ObjectOutputStream(baas)
                oos.use { oos ->
                    oos.writeObject(data)
                    oos.flush()
                }
            }
            return baos.toByteArray()
        }

        private fun <T> byteArray2Data(bytes: ByteArray): T? {
            var bais:ByteArrayInputStream? = null
            var ois:ObjectInputStream? = null
            try {
                bais = ByteArrayInputStream(bytes)
                ois = ObjectInputStream(bais)
                return ois.readObject() as T
            }finally {
                bais?.use {
                    it.close()
                }
                ois?.use {
                    it.close()
                }
            }

        }

    }

}
