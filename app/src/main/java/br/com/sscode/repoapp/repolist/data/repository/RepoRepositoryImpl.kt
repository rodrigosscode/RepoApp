package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource

class RepoRepositoryImpl(private val remote: RepoRemoteDataSource) : RepoRepository {

    override suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse {
        return remote.fetchRepos(language, sort, page)
    }
}