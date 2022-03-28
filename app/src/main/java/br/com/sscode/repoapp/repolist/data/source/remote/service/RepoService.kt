package br.com.sscode.repoapp.repolist.data.source.remote.service

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.data.source.remote.RepoRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService : RepoRemoteDataSource {

    @GET("search/repositories")
    override suspend fun fetchRepos(
        @Query("q") language: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
    ): RepoResponse

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}