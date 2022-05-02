package br.com.sscode.repoapp.repolist.di.data.module

import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepositoryImpl
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepository
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepositoryImpl
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import br.com.sscode.repoapp.repolist.di.data.qualifier.Cache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRemoteRepository(
        remoteDataSource: RepoRemoteDataSource,
    ): RepoRemoteRepository = RepoRemoteRepositoryImpl(remoteDataSource)

    @Singleton
    @Provides
    fun providesCacheRepository(
        @Cache localDataSource: RepoLocalDataSource
    ): RepoCacheRepository = RepoCacheRepositoryImpl(localDataSource)
}