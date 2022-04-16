package br.com.sscode.repoapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import br.com.sscode.cache.base.data.CacheDataKeys.PREFERENCES_CACHE

object RepoServiceLocator {

    lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    val Context.cache by preferencesDataStore(
        PREFERENCES_CACHE
    )
}