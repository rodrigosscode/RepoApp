package br.com.sscode.cache.core.pocketball.core

import com.google.gson.Gson
import java.lang.reflect.Type

internal class PocketBallCore<T> {

    private val gson: Gson by lazy { Gson() }

    fun crypt(data: T): String = gson.toJson(data)

    fun decrypt(data: String?, type: Type): T? {
        return try {
            gson.fromJson(data, type)
        } catch (ex: Exception) {
            null
        }
    }
}