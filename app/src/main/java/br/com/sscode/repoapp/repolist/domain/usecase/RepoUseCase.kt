package br.com.sscode.repoapp.repolist.domain.usecase

import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageCacheUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.remote.getrepopage.GetRepoPageRemoteUseCase

data class RepoUseCase(
    val getRepoPageRemoteUseCase: GetRepoPageRemoteUseCase,
    val getRepoPageCacheUseCase: GetRepoPageCacheUseCase,
    val putRepoPageCacheUseCase: PutRepoPageCacheUseCase
)
