package br.com.sscode.core.base.ui.fragment.extension

import androidx.fragment.app.Fragment
import br.com.sscode.core.util.isNetworkConnected

fun Fragment.hasNetworkConnection() = requireContext().isNetworkConnected()