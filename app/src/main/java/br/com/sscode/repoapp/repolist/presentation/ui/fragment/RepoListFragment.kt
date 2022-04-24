package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.sscode.core.base.ui.fragment.BaseFragment
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private var isLoadingRepos: Boolean = false
    private val repoList: MutableList<ItemDomain> by lazy {
        mutableListOf()
    }
    private val repoAdapter: RepoListAdapter by lazy {
        RepoListAdapter(requireContext(), repoList) {}
    }

    private val viewModel: RepoListViewModel by viewModels()

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
                    if (isLoadingRepos.not()) {
                        loadMore()
                    }
                }
            }
        )
    }

    private fun isEndOfList(
        scrollY: Int,
        view: NestedScrollView,
        oldScrollY: Int
    ) = with(binding) {
        (scrollY >= (rvRepos.measuredHeight - view.measuredHeight)) &&
                scrollY > oldScrollY
    }

    private fun loadMore() = with(viewModel) {
        fetchReposPaged(
            language = "language:kotlin",
            sort = " stars",
            page = nextPage
        )
    }

    private fun setupListRepoWithAdapter() = with(binding.rvRepos) {
        adapter = repoAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setupObservers() = with(viewModel) {
        reposResource.observeResource(viewLifecycleOwner,
            onSuccess = { items ->
                repoList.clear()
                repoList.addAll(items)
                repoAdapter.notifyDataSetChanged()
            },
            onLoading = ::setupLoading,
            onError = { error ->
                error.printStackTrace()
            }
        )
    }

    private fun setupLoading(isLoading: Boolean) = with(binding) {
        isLoadingRepos = isLoading
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
        if (existLoadedRepos.not()) {
            loadMore()
        }
    }
}