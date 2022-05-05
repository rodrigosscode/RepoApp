package br.com.sscode.repoapp.repolist.data.source.local

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface RepoLocalDataSource {
    suspend fun putPage(pageNumber: Int, repoPage: PagingData<ItemDomain>)
    suspend fun getPage(pageNumber: Int): PagingData<ItemDomain>?
}