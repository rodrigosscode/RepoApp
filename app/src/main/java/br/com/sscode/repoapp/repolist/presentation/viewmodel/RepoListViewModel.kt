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

    private val _reposResource: MutableLiveData<Resource<MutableList<ItemDomain>>> =
        MutableLiveData(Resource.Empty(mutableListOf()))
    val reposResource: LiveData<Resource<MutableList<ItemDomain>>> get() = _reposResource

    private var _existLoadedRepos: Boolean = false
    val existLoadedRepos: Boolean get() =  _existLoadedRepos

    private var _nextPage: Int = PagerManager.FIRST_PAGE
    val nextPage: Int get() = _nextPage

    fun fetchReposPaged(
        language: String,
        sort: String,
        page: Int
    ) = viewModelScope.launch {
        with(_reposResource) {
            try {
                loading(true)
                getRepoListPagedUseCase(language, sort, page).let { pagedData ->
                    addToReposLoaded(pagedData.items)?.run {
                        _existLoadedRepos = true
                        _nextPage = pagedData.pageManager.nextPage
                        success(this)
                    }
                }
            } catch (exception: Exception) {
                error(exception)
            } finally {
                loading(false)
            }
        }
    }

    private fun addToReposLoaded(items: List<ItemDomain>): MutableList<ItemDomain>? {
        _reposResource.value?.data?.let { data ->
            data.addAll(items)
            return data
        } ?: return null
    }
}