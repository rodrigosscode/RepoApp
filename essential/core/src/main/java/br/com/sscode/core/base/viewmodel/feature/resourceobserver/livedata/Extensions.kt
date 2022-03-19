package br.com.sscode.core.base.viewmodel.feature.resourceobserver.livedata

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
            is Resource.Success -> onSuccess.invoke(resource.data)
            is Resource.Error -> onError.invoke(resource.throwable)
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
            is Resource.Loading -> onLoading.invoke(resource.isLoading)
            is Resource.Success -> onSuccess.invoke(resource.data)
            is Resource.Error -> onError.invoke(resource.throwable)
        }
    }
}