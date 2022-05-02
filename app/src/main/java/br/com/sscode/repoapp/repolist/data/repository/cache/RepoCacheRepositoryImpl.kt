package br.com.sscode.repoapp.repolist.data.repository.cache

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import javax.inject.Inject

class RepoCacheRepositoryImpl @Inject constructor(
    private val local: RepoLocalDataSource
) : RepoCacheRepository {

    override suspend fun putRepoPage(page: Int, repoPage: RepoResponse) =
        local.putPage(page, repoPage)

    override suspend fun getRepoPage(page: Int): RepoResponse? = local.getPage(page)
}