package br.com.sscode.cache.feature

interface LocalCacheDataSource<T> {
    suspend fun save(data: T)
    suspend fun put(data: T)
    suspend fun get(): T?
    suspend fun contains(): Boolean
    suspend fun delete()
}