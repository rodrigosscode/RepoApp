package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface GetRepoListPagedUseCase {
    suspend operator fun invoke(language: String, sort: String, page: Int): PagingData<ItemDomain>
}