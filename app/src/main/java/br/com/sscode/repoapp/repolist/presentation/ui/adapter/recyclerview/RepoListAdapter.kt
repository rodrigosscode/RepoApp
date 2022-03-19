package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

class RepoListAdapter(
    private val context: Context
) : RecyclerView.Adapter<RepoListAdapter.Holder>() {

    private val items: List<RepoDomain.Item> = arrayListOf()

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
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

    inner class Holder(itemView: ItemRepoListBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bind() {
            //TODO not implemented yet
        }
    }
}