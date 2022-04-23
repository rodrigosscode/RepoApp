package br.com.sscode.core.base.data

sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean? = null,
    val throwable: Throwable? = null,
) {
    class Success<T>(
        data: T,
        isLoading: Boolean? = null,
        throwable: Throwable? = null
    ) : Resource<T>(data, isLoading, throwable)

    class Error<T>(
        throwable: Throwable,
        data: T? = null,
        isLoading: Boolean? = null
    ) : Resource<T>(data, isLoading, throwable)

    class Loading<T>(
        isLoading: Boolean,
        data: T? = null,
        throwable: Throwable? = null
    ) : Resource<T>(data, isLoading, throwable)

    class Empty<T>(data: T? = null) : Resource<T>(data = data)

    companion object {
        fun <T> Resource<T>.success(data: T): Success<T> =
            Success(data, isLoading, throwable)

        fun <T> Resource<T>.error(throwable: Throwable): Error<T> =
            Error(throwable, data, isLoading)

        fun <T> Resource<T>.loading(isLoading: Boolean): Loading<T> =
            Loading(isLoading, data, throwable)
    }
}