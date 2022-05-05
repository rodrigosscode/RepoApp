package br.com.sscode.repoapp.repolist.di.domain.module

import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepository
import br.com.sscode.repoapp.repolist.domain.usecase.RepoUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideRepoUseCase(
        getRepoListPagedUseCase: GetRepoListPagedUseCase,
        getRepoPageUseCase: GetRepoPageUseCase,
        putRepoPageUseCase: PutRepoPageUseCase
    ): RepoUseCase {
        return RepoUseCase(
            getRepoListPagedUseCase,
            getRepoPageUseCase,
            putRepoPageUseCase
        )
    }

    @Provides
    fun provideGetRepoListPagedUseCase(repository: RepoRemoteRepository): GetRepoListPagedUseCase {
        return GetRepoListPagedUseCaseImpl(repository)
    }

    @Provides
    fun provideGetRepoPageUseCase(repository: RepoCacheRepository): GetRepoPageUseCase {
        return GetRepoPageUseCaseImpl(repository)
    }

    @Provides
    fun providePutRepoPageUseCase(repository: RepoCacheRepository): PutRepoPageUseCase {
        return PutRepoPageUseCaseImpl(repository)
    }
}