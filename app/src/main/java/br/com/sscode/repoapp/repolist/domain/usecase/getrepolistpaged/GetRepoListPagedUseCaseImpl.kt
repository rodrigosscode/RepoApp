package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import br.com.sscode.repoapp.repolist.data.repository.RepoRepository
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import br.com.sscode.repoapp.repolist.domain.mapper.RepoMapper
import javax.inject.Inject

class GetRepoListPagedUseCaseImpl @Inject constructor(private val repository: RepoRepository) :
    GetRepoListPagedUseCase {

    override suspend fun invoke(language: String, sort: String, page: Int): List<RepoDomain.Item>? {
        val response = repository.fetchRepos(language, sort, page)
        return RepoMapper.convertResponseToDomain(response).items
    }
}