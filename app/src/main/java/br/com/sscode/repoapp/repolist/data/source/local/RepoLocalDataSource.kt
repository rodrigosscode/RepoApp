package br.com.sscode.repoapp.repolist.data.source.local

import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

interface RepoLocalDataSource {
    suspend fun saveRepo(repoDomain: RepoDomain)
    suspend fun getRepo(): RepoDomain?
}