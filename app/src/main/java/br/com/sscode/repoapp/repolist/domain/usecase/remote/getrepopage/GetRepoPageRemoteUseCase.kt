package br.com.sscode.repoapp.repolist.domain.usecase.remote.getrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

interface GetRepoPageRemoteUseCase {
    suspend operator fun invoke(language: String, sort: String, page: Int): PagingData<ItemDomain>
}