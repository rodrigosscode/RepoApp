package br.com.sscode.cache.core

interface LocalCacheDataSource<T> {
    suspend fun save(data: T)
    suspend fun get(to: Class<T>): T?
    suspend fun contains(): Boolean
    suspend fun delete()
}