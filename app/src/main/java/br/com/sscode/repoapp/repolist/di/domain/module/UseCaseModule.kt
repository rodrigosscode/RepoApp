package br.com.sscode.repoapp.repolist.di.domain.module

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepository
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.usecase.RepoUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.network.GetFromCacheOrRemoteUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.network.GetFromCacheOrRemoteUseCaseImpl
import br.com.sscode.repoapp.repolist.domain.usecase.remote.getrepopage.GetRepoPageRemoteUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.remote.getrepopage.GetRepoPageRemoteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetFromCacheOrRemoteUseCase(): GetFromCacheOrRemoteUseCase<PagingData<ItemDomain>> =
        GetFromCacheOrRemoteUseCaseImpl()

    @Provides
    fun provideRepoUseCase(
        getRepoPageRemoteUseCase: GetRepoPageRemoteUseCase,
        getRepoPageCacheUseCase: GetRepoPageCacheUseCase,
        putRepoPageCacheUseCase: PutRepoPageCacheUseCase
    ): RepoUseCase {
        return RepoUseCase(
            getRepoPageRemoteUseCase,
            getRepoPageCacheUseCase,
            putRepoPageCacheUseCase
        )
    }

    @Provides
    fun provideGetRepoListPagedUseCase(repository: RepoRemoteRepository): GetRepoPageRemoteUseCase {
        return GetRepoPageRemoteUseCaseImpl(repository)
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