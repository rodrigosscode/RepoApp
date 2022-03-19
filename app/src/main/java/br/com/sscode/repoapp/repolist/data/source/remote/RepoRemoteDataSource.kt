package br.com.sscode.repoapp.repolist.data.source.remote

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface RepoRemoteDataSource {
    suspend fun fetchRepos(
        language: String,
        sort: String,
        page: Int,
    ): RepoResponse
}