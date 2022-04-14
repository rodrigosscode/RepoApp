package br.com.sscode.cache.feature

import kotlinx.coroutines.flow.Flow

abstract class LocalCacheFeatureCore {

    var specificDataKey: String? = null

    abstract fun fromSpecificDataKey(key: String)
    abstract fun getData(): Flow<String?>
    abstract fun getDataKey(): String
}