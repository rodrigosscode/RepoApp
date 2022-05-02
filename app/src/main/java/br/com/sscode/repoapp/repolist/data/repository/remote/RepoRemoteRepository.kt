package br.com.sscode.repoapp.repolist.data.repository.remote

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface RepoRemoteRepository {
    suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse?
}