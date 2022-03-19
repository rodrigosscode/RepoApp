package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import kotlinx.coroutines.flow.Flow

class RepoListViewModel(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : ViewModel(), GetRepoListPagedUseCase.UI {

    override fun fetchRepoListPaged(): Flow<PagingData<RepoPageDomain.Item>>? {
        return getRepoListPagedUseCase(
            language = "language:kotlin",
            sort = "stars",
            page = 1
        )?.cachedIn(viewModelScope)
    }
}