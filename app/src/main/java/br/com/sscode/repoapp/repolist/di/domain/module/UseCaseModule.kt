package br.com.sscode.repoapp.repolist.di.domain.module

import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepository
import br.com.sscode.repoapp.repolist.domain.usecase.RepoUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoPagedRemoteUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoPagedRemoteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideRepoUseCase(
        getRepoPagedRemoteUseCase: GetRepoPagedRemoteUseCase,
        getRepoPageCacheUseCase: GetRepoPageCacheUseCase,
        putRepoPageCacheUseCase: PutRepoPageCacheUseCase
    ): RepoUseCase {
        return RepoUseCase(
            getRepoPagedRemoteUseCase,
            getRepoPageCacheUseCase,
            putRepoPageCacheUseCase
        )
    }

    @Provides
    fun provideGetRepoListPagedUseCase(repository: RepoRemoteRepository): GetRepoPagedRemoteUseCase {
        return GetRepoPagedRemoteUseCaseImpl(repository)
    }

    @Provides
    fun provideGetRepoPageUseCase(repository: RepoCacheRepository): GetRepoPageCacheUseCase {
        return GetRepoPageCacheUseCaseImpl(repository)
    }

    @Provides
    fun providePutRepoPageUseCase(repository: RepoCacheRepository): PutRepoPageCacheUseCase {
        return PutRepoPageCacheUseCaseImpl(repository)
    }
}