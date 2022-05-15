package br.com.sscode.repoapp.repolist.domain.usecase.network

interface GetFromCacheOrRemoteUseCase<T> {

    suspend operator fun invoke(
        isRefresh: Boolean,
        getRemoteCall: suspend () -> T,
        onGetRemoteCallSuccess: suspend (T) -> Unit,
        getCacheCall: suspend  () -> T?
    ): T?
}