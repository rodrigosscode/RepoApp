package br.com.sscode.repoapp.repolist.domain.usecase.network

interface GetFromCacheOrRemoteUseCase<T> {

    suspend operator fun invoke(
        isRefresh: Boolean,
        getFromRemoteCall: suspend () -> T,
        onGetFromRemoteCallSuccess: suspend (T) -> Unit,
        getFromCacheCall: suspend  () -> T?
    ): T?
}