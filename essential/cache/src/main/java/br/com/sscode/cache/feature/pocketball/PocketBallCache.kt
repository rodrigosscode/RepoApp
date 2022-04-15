package br.com.sscode.cache.feature.pocketball

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.sscode.cache.base.exception.*
import br.com.sscode.cache.feature.LocalCacheDataSource
import br.com.sscode.cache.feature.LocalCacheFeatureCore
import br.com.sscode.cache.feature.pocketball.core.PocketBallCore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class PocketBallCache<T : Any>(
    private val preferencesDataStore: DataStore<Preferences>,
    private val preferencesBaseDataKey: String,
) : LocalCacheDataSource<T>, LocalCacheFeatureCore() {

    private val pocketCore by lazy { PocketBallCore<T>() }

    @Throws(CacheException::class)
    override suspend fun save(data: T) {
        try {
            val cryptedData = pocketCore.crypt(data)

            preferencesDataStore.edit { preferences ->
                val key = stringPreferencesKey(getDataKey())
                preferences[key] = cryptedData
            }
        } catch (exception: Exception) {
            throw SaveCacheException(exception.message)
        }
    }

    @Throws(CacheException::class)
    override suspend fun get(type: Class<T>): T? {
        var valueReturned: T? = null
        return try {
            flow {
                if (contains()) {
                    val cryptedData = getData()
                    cryptedData?.let {
                        valueReturned = pocketCore.decrypt(it, type)
                    }
                }

                emit(valueReturned)
            }.first()
        } catch (exception: Exception) {
            throw GetCacheException(exception.message)
        }
    }

    @Throws(CacheException::class)
    override suspend fun put(data: T) {
        try {
            if (contains()) {
                delete()
            }

            save(data)
        } catch (exception: Exception) {
            throw PutCacheException(exception.message)
        }
    }

    @Throws(CacheException::class)
    override suspend fun contains(): Boolean {
        return getData().isNullOrEmpty().not()
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

    override suspend fun getData(): String? {
        return flow {
            preferencesDataStore.data.collect { preferences ->
                val key = stringPreferencesKey(getDataKey())
                val data = preferences[key]
                emit(data)
            }
        }.first()
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