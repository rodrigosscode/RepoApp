package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.viewmodel.BaseViewModel
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.error
import br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata.success
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : BaseViewModel() {

    private val _repos: MutableLiveData<Resource<List<RepoDomain.Item>>> = MutableLiveData()
    val repos: LiveData<Resource<List<RepoDomain.Item>>> get() = _repos

    fun fetchRepoListPaged() {
        viewModelScope.launch {
            with(_repos) {
                try {
                    val repos = getRepoListPagedUseCase(
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