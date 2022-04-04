package br.com.sscode.cache.core.pocketball

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.sscode.cache.core.LocalCacheDataSource
import br.com.sscode.cache.core.pocketball.core.PocketBallCore
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.collectLatest

abstract class PocketBall<T>(private val preferencesDataStore: DataStore<Preferences>) :
    LocalCacheDataSource<T> {

    private val pocketCore by lazy { PocketBallCore<T>() }
    abstract var preferencesKey: String

    override suspend fun save(data: T) {
        val cryptData = pocketCore.crypt(data)
        preferencesDataStore.edit { preferences ->
            val key = stringPreferencesKey(preferencesKey)
            preferences[key] = cryptData
        }
    }

    override suspend fun get(to: Class<T>): T? {
        var data: String? = null

        preferencesDataStore.data.collectLatest { preferences ->
            val key = stringPreferencesKey(preferencesKey)
            data = preferences[key]
        }

        return pocketCore.decrypt(data, to)
    }

    override suspend fun contains(): Boolean {
        return false
    }

    override suspend fun delete() {

    }
}