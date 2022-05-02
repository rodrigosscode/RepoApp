package br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface PutRepoPageUseCase {
    suspend operator fun invoke(page: Int, repoPage: RepoResponse)
}