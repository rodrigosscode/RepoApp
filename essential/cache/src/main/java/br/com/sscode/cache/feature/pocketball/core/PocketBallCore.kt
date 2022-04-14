package br.com.sscode.cache.feature.pocketball.core

import com.google.gson.Gson
import java.lang.reflect.Type

internal class PocketBallCore<T> {

    private val gson: Gson by lazy { Gson() }

    fun crypt(data: T): String = gson.toJson(data)

    fun decrypt(data: String, type: Class<T>): T? = gson.fromJson(data, type)
}