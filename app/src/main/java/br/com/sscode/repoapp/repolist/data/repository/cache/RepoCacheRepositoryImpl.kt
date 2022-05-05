package br.com.sscode.repoapp.repolist.data.repository.cache

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import javax.inject.Inject

class RepoCacheRepositoryImpl @Inject constructor(
    private val local: RepoLocalDataSource
) : RepoCacheRepository {

    override suspend fun putRepoPage(page: Int, repoPage: PagingData<ItemDomain>) =
        local.putPage(page, repoPage)

    override suspend fun getRepoPage(page: Int): PagingData<ItemDomain>? = local.getPage(page)
}