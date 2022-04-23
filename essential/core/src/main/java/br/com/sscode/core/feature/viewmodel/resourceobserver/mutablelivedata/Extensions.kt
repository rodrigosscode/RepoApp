package br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata

import androidx.lifecycle.MutableLiveData
import br.com.sscode.core.base.data.Resource
import br.com.sscode.core.base.data.Resource.Companion.error
import br.com.sscode.core.base.data.Resource.Companion.loading
import br.com.sscode.core.base.data.Resource.Companion.success

fun <T> MutableLiveData<Resource<T>>.success(data: T) =
    postValue(value?.success(data))

fun <T> MutableLiveData<Resource<T>>.error(throwable: Throwable) =
    postValue(value?.error(throwable))

fun <T> MutableLiveData<Resource<T>>.loading(isLoading: Boolean) {
    value = value?.loading(isLoading)
}