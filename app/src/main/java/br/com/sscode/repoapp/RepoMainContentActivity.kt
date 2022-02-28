package br.com.sscode.repoapp

import android.os.Bundle
import br.com.sscode.core.base.ui.BaseActivity
import br.com.sscode.repoapp.databinding.ActivityRepoMainContentBinding

class RepoMainContentActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoMainContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoMainContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}