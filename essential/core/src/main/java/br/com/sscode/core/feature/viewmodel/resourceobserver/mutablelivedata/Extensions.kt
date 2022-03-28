package br.com.sscode.core.feature.viewmodel.resourceobserver.mutablelivedata

import androidx.lifecycle.MutableLiveData
import br.com.sscode.core.base.data.Resource

fun <T> MutableLiveData<Resource<T>>.success(data: T) = postValue(Resource.success(data))

fun <T> MutableLiveData<Resource<T>>.error(throwable: Throwable) =
    postValue(Resource.error(throwable))

fun <T> MutableLiveData<Resource<T>>.loading(isLoading: Boolean) {
    value = Resource.loading(isLoading)
}