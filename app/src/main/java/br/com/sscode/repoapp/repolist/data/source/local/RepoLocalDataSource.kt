package br.com.sscode.repoapp.repolist.data.source.local

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

interface RepoLocalDataSource {
    suspend fun savePage(repoPage: PagingData<RepoDomain>)
    suspend fun getPage(): PagingData<RepoDomain>?
}