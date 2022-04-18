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
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : BaseViewModel() {

    private val _reposPaged: MutableLiveData<Resource<PagingData<ItemDomain>>> = MutableLiveData()
    val reposPaged: LiveData<Resource<PagingData<ItemDomain>>> get() = _reposPaged

    val existsReposLoaded: Boolean get() = _reposPaged.value != null

    private val _nextPage: MutableLiveData<Int> = MutableLiveData()
    private val nextPage: Int? get() = _nextPage.value

    fun fetchRepoListPaged(
        language: String = "language:kotlin",
        sort: String = "stars",
        page: Int = nextPage ?: PagerManager.FIRST_PAGE
    ) = viewModelScope.launch {
        with(_reposPaged) {
            try {
                loading(true)
                val pagedRepos = getRepoListPagedUseCase(
                    language = language,
                    sort = sort,
                    page = page
                )
                delay(2000)
                success(pagedRepos)
            } catch (exception: Exception) {
                error(exception)
            } finally {
                loading(false)
            }
        }
    }

    fun addLoadedPages(newItems: List<ItemDomain>) {

    }

    fun setNextPage(next: Int) {
        _nextPage.value = next
    }
}