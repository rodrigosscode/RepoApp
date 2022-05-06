package br.com.sscode.repoapp.repolist.domain.usecase

import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoPagedRemoteUseCase

data class RepoUseCase(
    val getRepoPagedRemoteUseCase: GetRepoPagedRemoteUseCase,
    val getRepoPageCacheUseCase: GetRepoPageCacheUseCase,
    val putRepoPageCacheUseCase: PutRepoPageCacheUseCase
)
