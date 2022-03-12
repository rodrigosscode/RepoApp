package br.com.sscode.repoapp.repolist.presentation.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sscode.repoapp.databinding.ItemRepoListBinding

class RepoListAdapter(
    private val context: Context
) : RecyclerView.Adapter<RepoListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = ItemRepoListBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(view.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount(): Int = 10

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemRepoListBinding by lazy {
            ItemRepoListBinding.bind(itemView)
        }
    }
}