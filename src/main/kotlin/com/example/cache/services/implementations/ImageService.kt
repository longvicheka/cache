package com.example.cache.services.implementations

import com.example.cache.repo.ICacheMachine
import com.example.cache.repo.ImageRepository
import com.example.cache.services.image.IImageService
import org.springframework.cache.annotation.EnableCaching
import com.example.cache.configs.CacheConfig.Companion.CACHE_NAME_BINARY
import com.example.cache.models.entities.Image
import org.springframework.stereotype.Service

@EnableCaching
@Service
class ImageService(
    private val repo: ImageRepository,
    private val cacheMachine: ICacheMachine
): IImageService {

    override fun getImage(id: String): Image? {
        cacheMachine.readBinary(CACHE_NAME_BINARY, id)?.run {
            return Image().apply { stream = this@run }
        }

        return repo.findByStatusTrueAndIdEquals(id)?.also {
            it.stream?.run {
                cacheMachine.saveBinary(CACHE_NAME_BINARY, id, this)
            }
        }
    }
}