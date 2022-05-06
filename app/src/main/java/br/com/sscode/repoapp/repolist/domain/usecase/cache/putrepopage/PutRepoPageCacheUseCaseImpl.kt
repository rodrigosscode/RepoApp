package br.com.sscode.repoapp.repolist.domain.usecase.cache.putrepopage

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.repository.cache.RepoCacheRepository
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import javax.inject.Inject

class PutRepoPageCacheUseCaseImpl @Inject constructor(
    private val repository: RepoCacheRepository
) : PutRepoPageCacheUseCase {

    override suspend fun invoke(page: Int, repoPage: PagingData<ItemDomain>) =
        repository.putRepoPage(page, repoPage)
}