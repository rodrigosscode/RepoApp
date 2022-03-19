package br.com.sscode.core.base.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        init()
    }

    open fun initViews() {}
    open fun initObservers() {}
    open fun init() {}
}