package br.com.sscode.repoapp.repolist.di.data.module

import br.com.sscode.repoapp.repolist.data.repository.RepoRepository
import br.com.sscode.repoapp.repolist.data.repository.RepoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(
        repository: RepoRepositoryImpl
    ): RepoRepository
}