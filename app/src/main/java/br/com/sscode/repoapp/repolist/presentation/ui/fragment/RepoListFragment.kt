package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sscode.core.base.ui.BaseFragment
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListPagedAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import br.com.sscode.repoapp.repolist.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by lazy {
        ViewModelFactory.getRepoListViewModel()
    }

    private val repoAdapter: RepoListPagedAdapter by lazy {
        RepoListPagedAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.fetchRepoListPaged()?.collectLatest {
                repoAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun init() {
        setupRepoAdapter()
    }

    private fun setupRepoAdapter() {
        binding.repos.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = repoAdapter
        }
    }
}