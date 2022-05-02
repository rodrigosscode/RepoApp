package br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import javax.inject.Inject

class GetRepoPageUseCaseImpl @Inject constructor(
    private val repository: RepoCacheRepository
) : GetRepoPageUseCase {

    override suspend fun invoke(page: Int): RepoResponse? =
        repository.getRepoPage(page)
}