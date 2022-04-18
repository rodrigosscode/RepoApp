package br.com.sscode.core.base.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        init()
    }

    open fun setupViews() {}
    open fun setupObservers() {}
    open fun init() {}
}