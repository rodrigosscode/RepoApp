package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.BaseViewModel
import br.com.sscode.core.feature.paging.PagerManager
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.error
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.loading
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.success
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.usecase.RepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoUseCase: RepoUseCase
) : BaseViewModel() {

    private val _reposResource: MutableLiveData<Resource<MutableList<ItemDomain>>> =
        MutableLiveData(Resource.Empty(mutableListOf()))
    val reposResource: LiveData<Resource<MutableList<ItemDomain>>> get() = _reposResource

    private var _existLoadedRepos: Boolean = false
    val existLoadedRepos: Boolean get() = _existLoadedRepos

    private var _nextPage: Int = PagerManager.FIRST_PAGE
    val nextPage: Int get() = _nextPage

    fun fetchReposPagedFromRemote(
        language: String,
        sort: String,
        page: Int
    ) = viewModelScope.launch {
        with(_reposResource) {
            try {
                loading(true)
                repoUseCase.getRepoPagedRemoteUseCase(language, sort, page).run {
                    repoUseCase.putRepoPageCacheUseCase(page, this)
                    onSuccessFetchReposPaged(this)
                }.also { currentWithNewRepos ->
                    loading(false)
                    currentWithNewRepos?.let {
                        success(it)
                    }
                }
            } catch (exception: Exception) {
                loading(false)
                error(exception)
            }
        }
    }

    fun fetchReposPagedFromCache(
        page: Int
    ) = viewModelScope.launch {
        with(_reposResource) {
            try {
                loading(true)
                repoUseCase.getRepoPageCacheUseCase(page)?.run {
                    onSuccessFetchReposPaged(this)
                }.also { currentWithNewRepos ->
                    loading(false)

                    currentWithNewRepos?.let {
                        success(it)
                    }
                }
            } catch (exception: Exception) {
                loading(false)
                error(exception)
            }
        }
    }

    private fun onSuccessFetchReposPaged(
        pagedData: PagingData<ItemDomain>
    ): MutableList<ItemDomain>? {
        return addToReposLoaded(pagedData.items)?.also {
            _existLoadedRepos = true
            _nextPage = pagedData.pageManager.nextPage
            return it
        }
    }

    private fun addToReposLoaded(items: List<ItemDomain>): MutableList<ItemDomain>? {
        _reposResource.value?.data?.let { data ->
            data.addAll(items)
            return data
        } ?: return null
    }
}