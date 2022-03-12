package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse

interface RepoPageRepository {
    suspend fun fetchRepoList(language: String, sort: String, page: Int): RepoPageResponse
}