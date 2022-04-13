package br.com.sscode.repoapp.repolist.data.source.local

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse

interface RepoLocalDataSource {
    suspend fun putPage(pageNumber: Int, repoPage: RepoResponse)
    suspend fun getPage(pageNumber: Int): RepoResponse?
}