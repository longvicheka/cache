package com.example.cache.repo

import kotlin.reflect.KClass

interface ICacheMachine {
    // save the given value into cache with the given name and value
    fun saveCache(name: String, key: String, value: Any)

    // save the given binary value into cache with given name and value
    fun saveBinary(name: String, key: String, value: Any)

    // read cache of a given name and key
    // return T: the cache value
    fun <T: Any> readObjectInternal(name: String, key: String, clazz: KClass<T>): T?

    // read cache of a given name and key
    // return String: the cache value
    fun readString(name: String, key: String, value: Any): String?

    // read cache of a given name and key
    // return ByteArray
    fun readBinary(name: String, key: String): ByteArray?
}

inline fun <reified T: Any> ICacheMachine.readObject(name: String, key: String): T? {
    return readObjectInternal(name, key, T::class)
}