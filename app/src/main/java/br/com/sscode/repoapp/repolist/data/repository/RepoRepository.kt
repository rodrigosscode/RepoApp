package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface RepoRepository {
    suspend fun fetchRepoList(language: String, sort: String, page: Int): RepoResponse
}