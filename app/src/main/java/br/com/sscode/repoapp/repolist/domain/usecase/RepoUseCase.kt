package br.com.sscode.repoapp.repolist.domain.usecase

import br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage.GetRepoPageUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage.PutRepoPageUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase

data class RepoUseCase(
    val getRepoListPagedUseCase: GetRepoListPagedUseCase,
    val getRepoPageUseCase: GetRepoPageUseCase,
    val putRepoPageUseCase: PutRepoPageUseCase
)
