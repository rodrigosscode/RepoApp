package br.com.sscode.core.base.data

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable) : Resource<T>()
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()

    companion object {
        fun <T> success(data: T): Success<T> = Success(data)
        fun <T> error(throwable: Throwable): Error<T> = Error(throwable)
        fun <T> loading(isLoading: Boolean): Loading<T> = Loading(isLoading)
    }
}