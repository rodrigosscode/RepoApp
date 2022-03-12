package br.com.sscode.repoapp.repolist.data.source.remote.service

import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoPageService {

    @GET("repositories")
    suspend fun fetchRepoList(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
    ): RepoPageResponse
}