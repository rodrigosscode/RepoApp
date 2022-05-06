package br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface GetRepoPageCacheUseCase {
    suspend operator fun invoke(page: Int): PagingData<ItemDomain>?
}