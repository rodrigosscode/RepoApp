package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.sscode.repoapp.repolist.data.paging.datasource.RepoPagingDataSource
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import br.com.sscode.repoapp.repolist.domain.mapper.RepoPageMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

@ExperimentalCoroutinesApi
class GetRepoListPagedUseCaseImpl(private val paging: RepoPagingDataSource) :
    GetRepoListPagedUseCase {

    override fun invoke(
        language: String,
        sort: String,
        page: Int
    ): Flow<PagingData<RepoPageDomain.Item>>? {
        return try {
            with(paging) {
                this.language = language
                this.sort = sort
                this.page = page
                return Pager(
                    initialKey = page,
                    config = PagingConfig(pageSize = 30, prefetchDistance = 10)
                ) {
                    this
                }.flow.mapLatest { paging ->
                    paging.map {
                        RepoPageMapper.convertItemResponseToDomain(it)
                    }
                }
            }
        } catch (ex: Exception) {
            null
        }
    }
}