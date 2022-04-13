package br.com.sscode.repoapp.repolist.di.data.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.data.source.local.cache.RepoListPagedCacheImpl
import br.com.sscode.repoapp.repolist.di.data.qualifier.Cache
import br.com.sscode.repoapp.repolist.di.global.qualifier.RepoPagedStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    @Cache
    fun provideLocalDataSourceService(
        @RepoPagedStore dataStore: DataStore<Preferences>
    ): RepoLocalDataSource = RepoListPagedCacheImpl(dataStore)
}