package br.com.sscode.repoapp.repolist.data.repository.remote

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import javax.inject.Inject

class RepoRemoteRepositoryImpl @Inject constructor(
    private val remote: RepoRemoteDataSource
) : RepoRemoteRepository {

    override suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse? =
        remote.fetchRepos(language, sort, page)
}