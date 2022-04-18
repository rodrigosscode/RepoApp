package br.com.sscode.repoapp

import android.os.Bundle
import br.com.sscode.core.base.ui.activity.BaseActivity
import br.com.sscode.repoapp.databinding.ActivityRepoMainContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoMainContentActivity : BaseActivity() {

    private lateinit var binding: ActivityRepoMainContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoMainContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}