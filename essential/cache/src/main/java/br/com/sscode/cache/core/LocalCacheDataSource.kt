package br.com.sscode.cache.core

interface LocalCacheDataSource<T> {
    suspend fun save(data: T)
    suspend fun get(): T?
    suspend fun contains(): Boolean
    suspend fun delete()
}