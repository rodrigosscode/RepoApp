package br.com.sscode.repoapp.repolist.domain.usecase.cache.getrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import javax.inject.Inject

class GetRepoPageCacheUseCaseImpl @Inject constructor(
    private val repository: RepoCacheRepository
) : GetRepoPageCacheUseCase {

    override suspend fun invoke(page: Int): PagingData<ItemDomain>? =
        repository.getRepoPage(page)
}