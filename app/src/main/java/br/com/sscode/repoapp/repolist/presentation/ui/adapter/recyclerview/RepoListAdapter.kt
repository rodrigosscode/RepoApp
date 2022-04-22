package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import com.bumptech.glide.Glide

class RepoListAdapter(
    private val context: Context,
    private val repoList: MutableList<ItemDomain>,
    private val onItemClick: (ItemDomain) -> Unit
) : RecyclerView.Adapter<RepoListAdapter.Holder>() {

    private val items: MutableList<ItemDomain> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = repoList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = repoList.size

    fun submitData(newItems: List<ItemDomain>) {
        val positionStart = items.size + 1
        var countItemsInserted = 0

        newItems.forEach { item ->
            if(!items.contains(item)) {
                items.add(item)
                countItemsInserted++
            }
        }

        notifyItemRangeInserted(positionStart, countItemsInserted)
    }

    inner class Holder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemDomain) = with(binding) {
            Glide.with(context).load(item.owner.avatarUrl).into(ivItemAuthorPhoto)
            tvItemRepoName.text = item.name
            tvItemAuthorName.text = item.owner.login
            tvItemCountRating.text = item.stargazersCount.toString()
            tvItemCountForks.text = item.forksCount.toString()

            root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}