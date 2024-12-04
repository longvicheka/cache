package com.example.cache.configs

import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager()
    }

    companion object {
        const val CACHE_NAME_JOB = "job"
        const val CACHE_NAME_BINARY = "binary"
        const val CACHE_TTL_MILLIS: Long = 1000 * 60 * 1
    }
}