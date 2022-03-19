package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain
import com.bumptech.glide.Glide

class RepoListPagedAdapter(private val context: Context) :
    PagingDataAdapter<RepoPageDomain.Item, RepoListPagedAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: RepoPageDomain.Item) = with(binding) {
            Glide.with(context).load(repo.owner.avatarUrl).into(itemAuthorPhoto)
            itemAuthorName.text = repo.owner.login
            itemCountForks.text = repo.forksCount.toString()
            itemCountRating.text = repo.stargazersCount.toString()
            itemRepoName.text = repo.name
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<RepoPageDomain.Item>() {

        override fun areItemsTheSame(
            oldItem: RepoPageDomain.Item,
            newItem: RepoPageDomain.Item
        ): Boolean {
            return oldItem == newItem && areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(
            oldItem: RepoPageDomain.Item,
            newItem: RepoPageDomain.Item
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}