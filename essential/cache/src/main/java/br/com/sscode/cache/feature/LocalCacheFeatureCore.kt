package br.com.sscode.cache.feature

abstract class LocalCacheFeatureCore {

    var specificDataKey: String? = null

    abstract fun fromSpecificDataKey(key: String)
    abstract suspend fun getData(): Any?
    abstract fun getDataKey(): String
}