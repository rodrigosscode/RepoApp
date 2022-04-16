package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import br.com.sscode.core.base.ui.BaseFragment
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
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
                "OPA",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupViews() {
        setupListRepoWithAdapter()
    }

    private fun setupListRepoWithAdapter() {
        binding.repos.apply {
            adapter = repoAdapter
        }
    }

    override fun setupObservers() = with(viewModel) {
        reposByPage.observeResource(
            this@RepoListFragment,
            onSuccess = { page ->
                repoAdapter.submitData(page.items)
            },
            onError = { error ->
                error.printStackTrace()
            },
            onLoading = ::setupLoading
        )
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        shimmerLoaderContent.root.apply {
            if (isLoading) {
                visibility = View.VISIBLE
                startShimmer()
            } else {
                visibility = View.GONE
                stopShimmer()
            }
        }
    }

    override fun init() = with(viewModel) {
        if (existsReposLoaded.not()) {
            fetchRepoListPaged()
        }
    }
}