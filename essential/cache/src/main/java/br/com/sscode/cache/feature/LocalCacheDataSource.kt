package br.com.sscode.cache.feature

import kotlinx.coroutines.flow.Flow

interface LocalCacheDataSource<T> {
    suspend fun save(data: T)
    suspend fun put(data: T)
    suspend fun get(type: Class<T>): Flow<T?>
    suspend fun contains(): Flow<Boolean>
    suspend fun delete()
}