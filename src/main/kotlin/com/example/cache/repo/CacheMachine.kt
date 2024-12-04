package com.example.cache.repo

import com.example.cache.common.Loggable
import com.example.cache.common.logger
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.cache.CacheManager
import com.example.cache.configs.CacheConfig
import org.springframework.stereotype.Component
import org.springframework.cache.get
import org.springframework.scheduling.annotation.Scheduled
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass

@Component
class CacheMachine(private val cacheManager: CacheManager): ICacheMachine, Loggable {

    override fun saveCache(name: String, key: String, value: Any) {
        cacheManager[name]?.apply {
            put(key, value)
        }
    }

    override fun saveBinary(name: String, key: String, value: Any) {
        cacheManager[name]?.apply {
            put(key, value)
        }
    }

    override fun <T : Any> readObjectInternal(name: String, key: String, clazz: KClass<T>): T? {
        cacheManager[name]?.apply {
            (get(key)?.get() as String)?.run {
                return jacksonObjectMapper().readValue(this, clazz.java)
            }
        }
        return null
    }

    override fun readString(name: String, key: String, value: Any): String? {
        return this.cacheManager[name]?.run {
            this.get(key)?.get() as String?
        }
    }

    override fun readBinary(name: String, key: String): ByteArray? {
        return this.cacheManager[name]?.run {
            get(key)?.get() as ByteArray?
        }
    }

    @Scheduled(fixedDelay = CacheConfig.CACHE_TTL_MILLIS, initialDelay = 0 )
    fun evictCache() {
        logger().info(
            "Flush all cache at: ${SimpleDateFormat().format(Date())}"
        )

        try {
            cacheManager.cacheNames.forEach {
                cacheManager[it]?.invalidate()
            }
        } catch (t: Throwable) {
            logger().error("Failed to invalidate cache: ${t.localizedMessage}")
        }
    }
}