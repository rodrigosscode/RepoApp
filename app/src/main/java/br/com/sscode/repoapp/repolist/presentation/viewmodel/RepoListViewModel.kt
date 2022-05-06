package br.com.sscode.repoapp.repolist.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.BaseViewModel
import br.com.sscode.core.feature.paging.PagerManager
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.error
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.success
import br.com.sscode.core.util.isNetworkConnected
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.usecase.RepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repoUseCase: RepoUseCase
) : BaseViewModel() {

    private val _reposResource: MutableLiveData<Resource<MutableList<ItemDomain>>> =
        MutableLiveData(Resource.Empty(mutableListOf()))
    val reposResource: LiveData<Resource<MutableList<ItemDomain>>> get() = _reposResource

    private var _existLoadedRepos: Boolean = false
    val existLoadedRepos: Boolean get() = _existLoadedRepos

    private var _nextPage: Int = PagerManager.FIRST_PAGE
    val nextPage: Int get() = _nextPage

    fun fetchReposPaged(
        language: String,
        sort: String,
        page: Int
    ) = viewModelScope.launch {
        with(_reposResource) {
            try {
//                loading(true)
                if (context.isNetworkConnected()) {
                    repoUseCase.getRepoPagedRemoteUseCase(language, sort, page).run {
                        onSuccessFetchReposPaged(this, page)
                    }
                } else {
                    repoUseCase.getRepoPageCacheUseCase(page)?.run {
                        onSuccessFetchReposPaged(this, page)
                    }
                }
            } catch (exception: Exception) {
                error(exception)
            } finally {
//                loading(false)
            }
        }
    }

    private suspend fun onSuccessFetchReposPaged(
        pagedData: PagingData<ItemDomain>,
        page: Int
    ) {
        with(_reposResource) {
            addToReposLoaded(pagedData.items)?.run {
                _existLoadedRepos = true
                _nextPage = pagedData.pageManager.nextPage
                success(this)
            }?.also {
                repoUseCase.putRepoPageCacheUseCase(page, pagedData)
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