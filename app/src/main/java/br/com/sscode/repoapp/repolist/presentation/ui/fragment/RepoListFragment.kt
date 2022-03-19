package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sscode.core.base.ui.BaseFragment
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import br.com.sscode.repoapp.repolist.presentation.viewmodel.ViewModelFactory

class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by lazy {
        ViewModelFactory.getRepoListViewModel()
    }
    private val repoAdapter: RepoListAdapter by lazy {
        RepoListAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        setupListRepo()
    }

    private fun setupListRepo() {
        binding.repos.apply {
            adapter = repoAdapter
        }
    }

    override fun initObservers() {
        viewModel.repos.observeResource(this,
            onSuccess = {

            },
            onError = {

            }
        )
    }

    override fun init() {
        viewModel.fetchRepoListPaged()
    }
}