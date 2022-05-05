package br.com.sscode.repoapp.repolist.data.repository.cache

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface RepoCacheRepository {
    suspend fun putRepoPage(page: Int, repoPage: PagingData<ItemDomain>)
    suspend fun getRepoPage(page: Int): PagingData<ItemDomain>?
}