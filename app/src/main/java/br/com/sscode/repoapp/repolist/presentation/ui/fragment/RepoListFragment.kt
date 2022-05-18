package br.com.sscode.repoapp.repolist.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.sscode.core.base.ui.fragment.BaseFragment
import br.com.sscode.core.base.ui.fragment.extension.hasNetworkConnection
import br.com.sscode.core.feature.viewmodel.resourceobserver.livedata.observeResource
import br.com.sscode.repoapp.databinding.FragmentRepoListBinding
import br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview.RepoListAdapter
import br.com.sscode.repoapp.repolist.presentation.viewmodel.RepoListViewModel
import br.com.sscode.ui.animation.feature.StaggerAnimation
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RepoListFragment : BaseFragment() {

    private lateinit var binding: FragmentRepoListBinding
    private var isLoadingRepos: Boolean = false
    private val repoAdapter: RepoListAdapter by lazy {
        RepoListAdapter {}
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
        setupParentScrollObserver()
        setupListRepoWithAdapter()
    }

    private fun setupParentScrollObserver() = with(binding) {
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

    private fun setupListRepoWithAdapter() = with(binding.rvRepos) {
        adapter = repoAdapter
        layoutManager = LinearLayoutManager(requireContext())
        itemAnimator = object : DefaultItemAnimator() {
            override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
                dispatchAddFinished(holder)
                dispatchAddStarting(holder)
                return false
            }
        }
    }

    override fun setupObservers() = with(viewModel) {
        reposResource.observeResource(
            viewLifecycleOwner,
            onSuccess = { newItems ->
                animateListReposToInsertNewItems()
                repoAdapter.submitData(newItems)
            },
            onError = { error ->
                error.printStackTrace()
            },
            onLoading = this@RepoListFragment::setViewLoading
        )
        scrollStateY.observe(
            viewLifecycleOwner,
            this@RepoListFragment::restoreScrollPositionView
        )
    }

    override fun init() = with(viewModel) {
        if (existLoadedRepos.not()) {
            loadMore()
        }
    }

    private fun loadMore() = with(viewModel) {
        fetchReposPage(
            isNetworkConnected = hasNetworkConnection(),
            language = "language:kotlin",
            sort = " stars",
            page = nextPage
        )
    }

    private fun setViewLoading(isLoading: Boolean) = with(binding) {
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

    private fun animateListReposToInsertNewItems() = binding.rvRepos.run {
        TransitionManager.beginDelayedTransition(this, StaggerAnimation())
    }

    private fun restoreScrollPositionView(stateScrollY: Int) = with(binding) {
        rvRepos.post {
            root.scrollY = stateScrollY
        }
    }

    private fun isEndOfList(
        scrollY: Int,
        view: NestedScrollView,
        oldScrollY: Int
    ): Boolean = with(binding) {
        (scrollY >= (rvRepos.measuredHeight - view.measuredHeight)) &&
                scrollY > oldScrollY
    }

    override fun onDestroyView() {
        viewModel.setScrollState(binding.root.scrollY)
        super.onDestroyView()
    }
}