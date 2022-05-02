package br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import javax.inject.Inject

class PutRepoPageUseCaseImpl @Inject constructor(
    private val repository: RepoCacheRepository
) : PutRepoPageUseCase {

    override suspend fun invoke(page: Int, repoPage: RepoResponse) =
        repository.putRepoPage(page, repoPage)
}