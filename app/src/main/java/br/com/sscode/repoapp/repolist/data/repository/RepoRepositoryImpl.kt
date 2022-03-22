package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(private val remote: RepoRemoteDataSource) :
    RepoRepository {

    override suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse {
        return remote.fetchRepos(language, sort, page)
    }
}