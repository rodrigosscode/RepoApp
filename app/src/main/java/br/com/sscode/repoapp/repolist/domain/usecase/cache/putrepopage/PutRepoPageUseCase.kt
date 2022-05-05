package br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface PutRepoPageUseCase {
    suspend operator fun invoke(page: Int, repoPage: PagingData<ItemDomain>)
}