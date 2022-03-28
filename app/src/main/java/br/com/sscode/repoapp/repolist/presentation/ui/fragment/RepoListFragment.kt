package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import br.com.sscode.core.base.ui.BaseFragment
import br.com.sscode.core.feature.paging.PagerManager
import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private val viewModel: RepoListViewModel by viewModels()
    private val repoAdapter: RepoListAdapter by lazy {
        RepoListAdapter(requireContext()) {
            Toast.makeText(
                requireContext(),
                currentPage.pageManager.currentPage.toString() ?: "OPA DEU B.O AQUI",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private lateinit var currentPage: PagingData<RepoDomain.Item>

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
        viewModel.repos.observeResource(
            this,
            onSuccess = {
                currentPage = it
                repoAdapter.submitData(currentPage.items)
            },
            onError = {

            },
            onLoading = this::setupLoading
        )
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        loader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun init() {
        viewModel.fetchRepoListPaged(page = PagerManager.FIRST_PAGE)
    }
}