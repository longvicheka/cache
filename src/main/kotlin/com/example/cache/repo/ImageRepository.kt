package com.example.cache.repo

import com.example.cache.models.entities.Image

interface ImageRepository {
    fun findByStatusTrueAndIdEquals(id: String): Image?
}