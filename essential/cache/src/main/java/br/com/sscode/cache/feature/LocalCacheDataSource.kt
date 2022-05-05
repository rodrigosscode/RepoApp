package br.com.sscode.cache.feature

import java.lang.reflect.Type

interface LocalCacheDataSource<T> {
    suspend fun save(data: T)
    suspend fun put(data: T)
    suspend fun get(typeToConvert: Type): T?
    suspend fun contains(): Boolean
    suspend fun delete()
}