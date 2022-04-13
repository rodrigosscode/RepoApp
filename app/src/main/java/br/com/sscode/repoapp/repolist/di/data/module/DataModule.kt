package br.com.sscode.repoapp.repolist.di.data.module

import android.content.Context
import br.com.sscode.repoapp.repolist.data.repository.RepoRepository
import br.com.sscode.repoapp.repolist.data.repository.RepoRepositoryImpl
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import br.com.sscode.repoapp.repolist.di.data.qualifier.Cache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesRepository(
        @ApplicationContext context: Context,
        remoteDataSource: RepoRemoteDataSource,
        @Cache localDataSource: RepoLocalDataSource
    ): RepoRepository = RepoRepositoryImpl(context, remoteDataSource, localDataSource)
}