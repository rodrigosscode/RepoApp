package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import br.com.sscode.repoapp.repolist.data.repository.RepoPageRepository
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import br.com.sscode.repoapp.repolist.domain.mapper.RepoPageMapper

class GetRepoListPagedUseCaseImpl(private val repository: RepoPageRepository): GetRepoListPagedUseCase {

    override suspend fun invoke(language: String, sort: String, page: Int): RepoPageDomain? {
        return try {
            val response = repository.fetchRepoList(language, sort, page)
            RepoPageMapper.convertRepoPageResponseToDomain(response)
        } catch (ex: Exception) {
            null
        }
    }
}