package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import com.bumptech.glide.Glide

class RepoListAdapter(
    private val onItemClick: (ItemDomain) -> Unit
) : ListAdapter<ItemDomain, RepoListAdapter.Holder>(ItemDomainDiffUtil) {

    object ItemDomainDiffUtil : DiffUtil.ItemCallback<ItemDomain>() {

        override fun areItemsTheSame(oldItem: ItemDomain, newItem: ItemDomain): Boolean {
            return oldItem.name == newItem.name && oldItem.owner.login == newItem.owner.login
        }

        override fun areContentsTheSame(oldItem: ItemDomain, newItem: ItemDomain): Boolean {
            return oldItem == newItem && areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun submitData(newItems: List<ItemDomain>) {
        val countInitial = currentList.size
        val positionStart = countInitial + 1
        submitList(newItems)
        notifyItemRangeInserted(positionStart, currentList.size)
    }

    inner class Holder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            ViewGroupCompat.setTransitionGroup(itemView as ViewGroup, true)
        }

        fun bind(item: ItemDomain) = with(binding) {
            loadDataInViews(item)
            setListeners(item)
        }

        private fun loadDataInViews(item: ItemDomain) = with(binding) {
            Glide.with(itemView).load(item.owner.avatarUrl).into(ivItemAuthorPhoto)
            tvItemRepoName.text = item.name
            tvItemAuthorName.text = item.owner.login
            tvItemCountRating.text = item.stargazersCount.toString()
            tvItemCountForks.text = item.forksCount.toString()
        }

        private fun setListeners(item: ItemDomain) = with(binding) {
            root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}