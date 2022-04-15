package br.com.sscode.repoapp.repolist.data.repository

import android.content.Context
import br.com.sscode.core.util.isNetworkConnected
import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.local.RepoLocalDataSource
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val context: Context,
    private val remote: RepoRemoteDataSource,
    private val localCache: RepoLocalDataSource
) : RepoRepository {

    override suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse? {
        return with(context) {
            if (isNetworkConnected()) {
                remote.fetchRepos(language, sort, page)?.let { response ->
                    localCache.putPage(page, response)
                    return response
                }
            } else {
                return localCache.getPage(page)
            }
        }
    }
}