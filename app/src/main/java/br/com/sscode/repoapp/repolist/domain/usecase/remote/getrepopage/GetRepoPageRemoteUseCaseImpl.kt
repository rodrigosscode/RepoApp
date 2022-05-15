package br.com.sscode.repoapp.repolist.domain.usecase.remote.getrepopage

import br.com.sscode.core.feature.paging.PagerManager
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.remote.RepoRemoteRepository
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.mapper.convertResponseToDomain
import javax.inject.Inject

class GetRepoPageRemoteUseCaseImpl @Inject constructor(
    private val repository: RepoRemoteRepository
) : GetRepoPageRemoteUseCase {

    override suspend fun invoke(
        language: String,
        sort: String,
        page: Int
    ): PagingData<ItemDomain> {
        return repository.fetchRepos(language, sort, page)?.let { response ->
            PagingData(
                convertResponseToDomain(response).items,
                PagerManager.build(page)
            )
        } ?: throw IllegalArgumentException("Didn't get a good response")
    }
}