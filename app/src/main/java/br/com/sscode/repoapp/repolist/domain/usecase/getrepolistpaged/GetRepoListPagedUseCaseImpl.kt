package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import br.com.sscode.core.feature.paging.PagerManager
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.RepoRepository
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import br.com.sscode.repoapp.repolist.domain.mapper.RepoMapper
import javax.inject.Inject

class GetRepoListPagedUseCaseImpl @Inject constructor(private val repository: RepoRepository) :
    GetRepoListPagedUseCase {

    override suspend fun invoke(
        language: String,
        sort: String,
        page: Int
    ): PagingData<RepoDomain.Item>? {
        val response = repository.fetchRepos(language, sort, page)
        return response?.let {
            val domain = RepoMapper.convertResponseToDomain(it)
            PagingData(
                domain.items,
                PagerManager.build(page)
            )
        } ?: return null
    }
}