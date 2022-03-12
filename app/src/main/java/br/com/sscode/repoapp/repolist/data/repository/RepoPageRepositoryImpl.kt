package br.com.sscode.repoapp.repolist.data.repository

import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse
import br.com.sscode.repoapp.repolist.data.source.remote.api.RetrofitApi
import br.com.sscode.repoapp.repolist.data.source.remote.service.RepoPageService

class RepoPageRepositoryImpl : RepoPageRepository {

    private val service: RepoPageService by lazy { RetrofitApi().getRepoPageService() }

    override suspend fun fetchRepoList(language: String, sort: String, page: Int): RepoPageResponse {
        return service.fetchRepoList(language, sort, page)
    }
}