package br.com.sscode.repoapp.repolist.data.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse
import br.com.sscode.repoapp.repolist.data.repository.RepoPageRepository

class RepoPagingDataSource(private val repository: RepoPageRepository) :
    PagingSource<Int, RepoPageResponse.Item>() {

    var language: String = "language:kotlin"
    var sort: String = "stars"
    var page: Int = 1

    override fun getRefreshKey(state: PagingState<Int, RepoPageResponse.Item>): Int? {
        return state.anchorPosition?.let { position ->
            val pageResult = state.closestPageToPosition(position)
            pageResult?.prevKey?.plus(1) ?: pageResult?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoPageResponse.Item> {
        return try {
            page = params.key ?: 1
            val nextPagerNumber = page.plus(1)
            val response: RepoPageResponse = repository.fetchRepoList(
                language = language,
                sort = sort,
                page = page
            )
            val data = response.items
            LoadResult.Page(data = data, prevKey = null, nextKey = nextPagerNumber)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}