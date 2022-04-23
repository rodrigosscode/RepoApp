package br.com.sscode.core.feature.viewmodel.resourceobserver.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import br.com.sscode.core.base.data.Resource

fun <T> LiveData<Resource<T>>.observeResource(
    lifeCycleOwner: LifecycleOwner,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    observe(lifeCycleOwner) { resource ->
        when (resource) {
            is Resource.Success -> {
                resource.data?.let(onSuccess)
            }
            is Resource.Error -> {
                resource.throwable?.let(onError)
            }
        }
    }
}

fun <T> LiveData<Resource<T>>.observeResource(
    lifeCycleOwner: LifecycleOwner,
    onLoading: (Boolean) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    observe(lifeCycleOwner) { resource ->
        when (resource) {
            is Resource.Loading -> {
                resource.isLoading?.let(onLoading)
            }
            is Resource.Success -> {
                resource.data?.let(onSuccess)
            }
            is Resource.Error -> {
                resource.throwable?.let(onError)
            }
        }
    }
}