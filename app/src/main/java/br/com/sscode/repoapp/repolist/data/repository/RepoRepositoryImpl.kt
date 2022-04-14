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
    private val local: RepoLocalDataSource
) : RepoRepository {

    override suspend fun fetchRepos(language: String, sort: String, page: Int): RepoResponse? =
        with(context) {
            return if (isNetworkConnected()) {
                val repoResponse = remote.fetchRepos(language, sort, page)
                repoResponse?.let { local.putPage(page, it) }
                repoResponse
            } else {
                local.getPage(page)
            }
        }
}