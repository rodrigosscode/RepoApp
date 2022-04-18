package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sscode.core.base.ui.fragment.BaseFragment
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private var isLoadingNewRepos: Boolean = false
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
        setupParentScroll()
        setupListRepoWithAdapter()
    }

    private fun setupParentScroll() = with(binding) {
        root.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { view, _, scrollY, _, oldScrollY ->
                if (isEndOfList(scrollY, view, oldScrollY)) {
                    if (isLoadingNewRepos.not()) {
                        loadMore()
                    }
                }
            })
    }

    private fun isEndOfList(
        scrollY: Int,
        view: NestedScrollView,
        oldScrollY: Int
    ) = with(binding) {
        (scrollY >= (repos.measuredHeight - view.measuredHeight)) &&
                scrollY > oldScrollY
    }

    private fun loadMore() {
        viewModel.fetchRepoListPaged()
    }

    private fun setupListRepoWithAdapter() = with(binding.repos) {
        adapter = repoAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObservers() = with(viewModel) {
        reposPaged.observeResource(
            this@RepoListFragment,
            onSuccess = { page ->
                viewModel.addLoadedPages(page.items)
                viewModel.setNextPage(page.pageManager.nextPage)
                repoAdapter.submitData(page.items)
            },
            onError = { error ->
                error.printStackTrace()
            },
            onLoading = ::setupLoading
        )
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        isLoadingNewRepos = isLoading
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
            loadMore()
        }
    }
}