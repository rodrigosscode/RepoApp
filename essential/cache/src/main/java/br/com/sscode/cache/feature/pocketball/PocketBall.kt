package br.com.sscode.cache.feature.pocketball

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.sscode.cache.base.exception.*
import br.com.sscode.cache.feature.LocalCacheDataSource
import br.com.sscode.cache.feature.LocalCacheFeatureCore
import br.com.sscode.cache.feature.pocketball.core.PocketBallCore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class PocketBall<T>(
    private val preferencesDataStore: DataStore<Preferences>,
    private val preferencesBaseDataKey: String,
) : LocalCacheDataSource<T>, LocalCacheFeatureCore() {

    private val pocketCore by lazy { PocketBallCore<T>() }

    @Throws(CacheException::class)
    override suspend fun save(data: T) {
        try {
            val cryptData = pocketCore.crypt(data)

            preferencesDataStore.edit { preferences ->
                val key = stringPreferencesKey(getDataKey())
                preferences[key] = cryptData
            }
        } catch (exception: Exception) {
            throw SaveCacheException(exception.message)
        }
    }

    @Throws(CacheException::class)
    override suspend fun get(type: Class<T>): Flow<T?> {
        return try {
            val contains = contains().first()

            flow {
                if (contains) {
                    val data = getData().first()
                    data?.let {
                        emit(
                            pocketCore.decrypt(it, type)
                        )
                    } ?: emit(null)
                } else {
                    emit(null)
                }
            }
        } catch (exception: Exception) {
            throw GetCacheException(exception.message)
        }
    }


    @Throws(CacheException::class)
    override suspend fun put(data: T) {
        try {
            val contains = contains().first()

            if (contains) {
                delete()
            }

            save(data)
        } catch (exception: Exception) {
            throw PutCacheException(exception.message)
        }
    }

    @Throws(CacheException::class)
    override suspend fun contains(): Flow<Boolean> {
        return flow {
            preferencesDataStore.data.collect { preferences ->
                val key = stringPreferencesKey(getDataKey())
                val data = preferences[key]
                emit(
                    data.isNullOrBlank().not()
                )
            }
        }
    }

    @Throws(CacheException::class)
    override suspend fun delete() {
        try {
            preferencesDataStore.edit { preferences ->
                val key = stringPreferencesKey(getDataKey())
                preferences.remove(key)
            }
        } catch (exception: Exception) {
            throw DeleteCacheException(exception.message)
        }
    }

    override fun getData(): Flow<String?> {
        return flow {
            preferencesDataStore.data.collect { preferences ->
                val key = stringPreferencesKey(getDataKey())
                val data = preferences[key]
                emit(data)
            }
        }
    }

    override fun fromSpecificDataKey(key: String) {
        specificDataKey = key
    }

    override fun getDataKey(): String {
        return specificDataKey?.trim()?.let { specificKey ->
            "${preferencesBaseDataKey}_${specificKey}"
        } ?: preferencesBaseDataKey
    }
}