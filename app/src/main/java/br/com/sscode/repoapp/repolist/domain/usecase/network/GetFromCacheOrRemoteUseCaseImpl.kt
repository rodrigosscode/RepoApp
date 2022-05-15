package br.com.sscode.repoapp.repolist.domain.usecase.network

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

class GetFromCacheOrRemoteUseCaseImpl : GetFromCacheOrRemoteUseCase<PagingData<ItemDomain>> {

    override suspend fun invoke(
        isRefresh: Boolean,
        getRemoteCall: suspend () -> PagingData<ItemDomain>,
        onGetRemoteCallSuccess: suspend (PagingData<ItemDomain>) -> Unit,
        getCacheCall: suspend () -> PagingData<ItemDomain>?
    ): PagingData<ItemDomain>? {
        return if (isRefresh) {
            val returnRemoteCall = getRemoteCall.invoke()
            onGetRemoteCallSuccess.invoke(returnRemoteCall)
            returnRemoteCall
        } else {
            getCacheCall.invoke()
        }
    }
}