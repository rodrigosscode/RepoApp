package br.com.sscode.cache.feature.pocketball

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.sscode.cache.base.exception.*
import br.com.sscode.cache.feature.LocalCacheDataSource
import br.com.sscode.cache.feature.LocalCacheFeatureCore
import br.com.sscode.cache.feature.pocketball.core.PocketBallCore
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

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
    override suspend fun get(): T? {
        var data: String? = null
        val type = object : TypeToken<T>() {}.type

        return try {
            if (contains()) {
                preferencesDataStore.data.collectLatest { preferences ->
                    val key = stringPreferencesKey(getDataKey())
                    data = preferences[key]
                }

                data?.let {
                    pocketCore.decrypt(it, type)
                } ?: return null

            } else null
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
        var data: String? = null

        preferencesDataStore.data.collectLatest { preferences ->
            val key = stringPreferencesKey(getDataKey())
            data = preferences[key]
        }

        return data.isNullOrBlank().not()
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

    override fun fromSpecificDataKey(key: String) {
        specificDataKey = key
    }

    override fun getDataKey(): String {
        return specificDataKey?.trim()?.let { specificKey ->
            "${preferencesBaseDataKey}_${specificKey}"
        } ?: preferencesBaseDataKey
    }
}