package br.com.sscode.core.base.data

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable) : Resource<T>()
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()

    companion object {
        fun <T> success(data: T): Resource.Success<T> = Resource.Success(data)
        fun <T> error(throwable: Throwable): Resource.Error<T> = Resource.Error(throwable)
        fun <T> loading(isLoading: Boolean): Resource.Loading<T> = Resource.Loading(isLoading)
    }
}