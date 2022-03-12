package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain

interface GetRepoListPagedUseCase {

    suspend operator fun invoke(language: String, sort: String, page: Int): RepoPageDomain?

    interface UI {
        fun fetchRepoListPaged()
    }
}