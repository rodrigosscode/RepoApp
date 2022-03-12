package br.com.sscode.repoapp.repolist.presentation.viewmodel

import br.com.sscode.repoapp.repolist.data.repository.RepoPageRepositoryImpl
import br.com.sscode.repoapp.repolist.domain.usecase.getrepolistpaged.GetRepoListPagedUseCaseImpl

object ViewModelFactory {

    fun getRepoListViewModel(): RepoListViewModel {
        return RepoListViewModel(GetRepoListPagedUseCaseImpl(RepoPageRepositoryImpl()))
    }
}