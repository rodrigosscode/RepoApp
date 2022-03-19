package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import br.com.sscode.repoapp.repolist.data.source.remote.api.RetrofitApi
import br.com.sscode.repoapp.repolist.data.source.remote.service.RepoService

class RepoRepositoryImpl(private val remote: RepoRemoteDataSource) : RepoRepository {

    override suspend fun fetchRepoList(language: String, sort: String, page: Int): RepoResponse {
        return remote.fetchRepoList(language, sort, page)
    }
}