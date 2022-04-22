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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : BaseViewModel() {

    private val _repos: MutableLiveData<Resource<MutableList<ItemDomain>>> = MutableLiveData()
    val repos: LiveData<Resource<MutableList<ItemDomain>>> get() = _repos

    private val _repoPagesLoaded: MutableLiveData<MutableList<ItemDomain>> =
        MutableLiveData(mutableListOf())

    private val _existsReposLoaded: MutableLiveData<Boolean> = MutableLiveData(false)
    val existsReposLoaded: Boolean get() = _existsReposLoaded.value ?: false

    private val _nextPage: MutableLiveData<Int> = MutableLiveData()
    private val nextPage: Int get() = _nextPage.value ?: PagerManager.FIRST_PAGE

    fun fetchRepoListPaged(
        language: String = "language:kotlin",
        sort: String = "stars",
        page: Int = nextPage
    ) = viewModelScope.launch {
        with(_repos) {
            try {
                loading(true)
                val pagedRepos = getRepoListPagedUseCase(
                    language = language,
                    sort = sort,
                    page = page
                )
                delay(2000)
                _nextPage.value = pagedRepos.pageManager.nextPage
                _existsReposLoaded.value = true
                _repoPagesLoaded.value?.let { list ->
                    list.addAll(pagedRepos.items)
                    success(list)
                }
            } catch (exception: Exception) {
                error(exception)
            } finally {
                loading(false)
            }
        }
    }
}