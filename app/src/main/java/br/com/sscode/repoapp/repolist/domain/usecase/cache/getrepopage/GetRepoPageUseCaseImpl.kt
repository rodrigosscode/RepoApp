package br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import javax.inject.Inject

class GetRepoPageUseCaseImpl @Inject constructor(
    private val repository: RepoCacheRepository
) : GetRepoPageUseCase {

    override suspend fun invoke(page: Int): PagingData<ItemDomain>? =
        repository.getRepoPage(page)
}