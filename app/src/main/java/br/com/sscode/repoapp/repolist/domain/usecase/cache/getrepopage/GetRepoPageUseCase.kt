package br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface GetRepoPageUseCase {
    suspend operator fun invoke(page: Int): RepoResponse?
}