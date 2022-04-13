package br.com.sscode.repoapp.repolist.di.global.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import br.com.sscode.repoapp.repolist.di.global.qualifier.RepoPagedStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val PREFERENCES_CACHE = "PREFERENCES_CACHE"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.cache by preferencesDataStore(
        PREFERENCES_CACHE
    )

    @Singleton
    @Provides
    @RepoPagedStore
    fun provideDataStoreCacheRepoListPaged(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.cache
}