package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.BaseViewModel
import br.com.sscode.core.feature.paging.PagerManager
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

    private val _resource: MutableLiveData<Resource<MutableList<ItemDomain>>> = MutableLiveData(
        Resource.Empty(mutableListOf())
    )
    val resource: LiveData<Resource<MutableList<ItemDomain>>> get() = _resource

    val existsReposLoaded: Boolean get() = _resource.value?.data?.isNotEmpty() ?: false

    private val _nextPage: MutableLiveData<Int> = MutableLiveData()
    private val nextPage: Int get() = _nextPage.value ?: PagerManager.FIRST_PAGE

    fun fetchRepoListPaged(
        language: String = "language:kotlin",
        sort: String = "stars",
        page: Int = nextPage
    ) = viewModelScope.launch {
        with(_resource) {
            try {
                loading(true)
                val pagedRepos = getRepoListPagedUseCase(language, sort, page)
                value?.data?.let { data ->
                    _nextPage.value = pagedRepos.pageManager.nextPage
                    data.addAll(pagedRepos.items)
                    success(data)
                }
            } catch (ex: Exception) {
                error(ex)
            } finally {
                loading(false)
            }
        }
    }
}