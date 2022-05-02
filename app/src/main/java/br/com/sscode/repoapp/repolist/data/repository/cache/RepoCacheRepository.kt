package br.com.sscode.repoapp.repolist.data.repository.cache

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface RepoCacheRepository {
    suspend fun putRepoPage(page: Int, repoPage: RepoResponse)
    suspend fun getRepoPage(page: Int): RepoResponse?
}