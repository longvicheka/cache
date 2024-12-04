package com.example.cache.models.entities

import jakarta.persistence.*

@Entity
@Table(schema = "job", name = "image")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(name = "app")
    val app: String? = null,

    @Column(name = "type")
    val type: String? = null,

    @Column(name = "stream")
    var stream: ByteArray? = null,

    @Column(name = "active")
    val active: Boolean = true,
)
