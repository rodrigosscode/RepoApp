package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.feature.resourceobserver.mutablelivedata.error
import br.com.sscode.core.base.viewmodel.feature.resourceobserver.mutablelivedata.success
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : ViewModel() {

    private val _repos: MutableLiveData<Resource<RepoDomain>> = MutableLiveData()
    val repos: LiveData<Resource<RepoDomain>> get() = _repos

    fun fetchRepoListPaged() {
        viewModelScope.launch {
            with(_repos) {
                try {
                    val repos: RepoDomain? = getRepoListPagedUseCase(
                        language = "language:kotlin",
                        sort = "stars",
                        page = 1
                    )
                    repos?.let {
                        success(it)
                    }
                } catch (exception: Exception) {
                    error(exception)
                }
            }
        }
    }
}