package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain
import com.bumptech.glide.Glide

class RepoListAdapter(
    private val context: Context,
    private val onItemClick: (RepoDomain.Item) -> Unit
) : RecyclerView.Adapter<RepoListAdapter.Holder>() {

    private val items: MutableList<RepoDomain.Item> = arrayListOf()

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
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun submitData(newItems: List<RepoDomain.Item>) {
        var position = -1
        for (item in newItems) {
            items.add(item)
            position = items.size - 1
            notifyItemInserted(position)
        }
    }

    inner class Holder(private val binding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoDomain.Item) = with(binding) {
            Glide.with(context).load(item.owner.avatarUrl).into(itemAuthorPhoto)
            itemRepoName.text = item.name
            itemAuthorName.text = item.owner.login
            itemCountRating.text = item.stargazersCount.toString()
            itemCountForks.text = item.forksCount.toString()

            root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}