package br.com.sscode.repoapp.repolist.presentation.viewmodel

import androidx.lifecycle.*
import br.com.sscode.repoapp.repolist.data.repository.RepoPageRepositoryImpl
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCase
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCaseImpl
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val getRepoListPagedUseCase: GetRepoListPagedUseCase
) : ViewModel(), GetRepoListPagedUseCase.UI {

    private val _repos: MutableLiveData<RepoPageDomain> = MutableLiveData()
    val repos: LiveData<RepoPageDomain> get() = _repos

    override fun fetchRepoListPaged()  {
        viewModelScope.launch {
            with(_repos) {
                val repos: RepoPageDomain? = getRepoListPagedUseCase(
                    language = "language:kotlin",
                    sort = "stars",
                    page = 1
                )
                repos?.let {
                    postValue(it)
                }
            }
        }
    }
}