package br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged

import androidx.paging.PagingData
import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import kotlinx.coroutines.flow.Flow

interface GetRepoListPagedUseCase {

    operator fun invoke(language: String, sort: String, page: Int): Flow<PagingData<RepoPageDomain.Item>>?

    interface UI {
        fun fetchRepoListPaged(): Flow<PagingData<RepoPageDomain.Item>>?
    }
}