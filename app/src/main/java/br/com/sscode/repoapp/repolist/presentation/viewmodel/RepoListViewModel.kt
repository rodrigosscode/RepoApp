package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.BaseViewModel
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.error
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.loading
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.success
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : BaseViewModel() {

    private val _repos: MutableLiveData<Resource<PagingData<ItemDomain>>> = MutableLiveData()
    val repos: LiveData<Resource<PagingData<ItemDomain>>> get() = _repos

    fun fetchRepoListPaged(
        language: String = "language:kotlin",
        sort: String = "stars",
        page: Int
    ) = viewModelScope.launch {
        with(_repos) {
            try {
                loading(true)
                val pagedRepos = getRepoListPagedUseCase(
                    language = language,
                    sort = sort,
                    page = page
                )
                pagedRepos?.let {
                    success(it)
                }
            } catch (exception: Exception) {
                error(exception)
            } finally {
                loading(false)
            }
        }
    }
}