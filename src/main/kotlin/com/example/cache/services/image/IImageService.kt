package com.example.cache.services.image

import com.example.cache.models.entities.Image

interface IImageService {
    fun getImage(id: String): Image?
}