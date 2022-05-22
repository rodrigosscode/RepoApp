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
import br.com.sscode.repoapp.repolist.domain.usecase.network.GetFromCacheOrRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoUseCase: RepoUseCase,
    private val getFromCacheOrRemoteUseCase: GetFromCacheOrRemoteUseCase<PagingData<ItemDomain>>
) : BaseViewModel() {

    private val _reposResource: MutableLiveData<Resource<MutableList<ItemDomain>>> =
        MutableLiveData(Resource.Empty(mutableListOf()))
    val reposResource: LiveData<Resource<MutableList<ItemDomain>>> get() = _reposResource

    private var _existLoadedRepos: Boolean = false
    val existLoadedRepos: Boolean get() = _existLoadedRepos

    private var _nextPage: Int = PagerManager.FIRST_PAGE
    val nextPage: Int get() = _nextPage

    private var _scrollStateY: MutableLiveData<Int> = MutableLiveData()
    val scrollStateY: LiveData<Int> get() = _scrollStateY

    fun fetchReposPage(
        isNetworkConnected: Boolean,
        language: String,
        sort: String,
        page: Int
    ) = viewModelScope.launch {
        with(_reposResource) {
            try {
                loading(true)
                with(repoUseCase) {
                    getFromCacheOrRemoteUseCase(
                        isRefresh = isNetworkConnected,
                        getFromRemoteCall = {
                            getRepoPageRemoteUseCase(language, sort, page)
                        },
                        onGetFromRemoteCallSuccess = { page ->
                            putRepoPageCacheUseCase(page.pageManager.currentPage, page)
                        },
                        getFromCacheCall = {
                            getRepoPageCacheUseCase(page)
                        }
                    )
                }.let { pageReturned ->
                    loading(false)
                    pageReturned?.run {
                        onSuccessFetchReposPaged(this)?.run {
                            success(this)
                        }
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

    fun setScrollState(scrollY: Int) {
        _scrollStateY.value = scrollY
    }
}