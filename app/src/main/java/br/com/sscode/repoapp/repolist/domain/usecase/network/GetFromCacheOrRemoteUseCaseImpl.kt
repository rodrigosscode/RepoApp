package br.com.sscode.repoapp.repolist.domain.usecase.network

import br.com.sscode.core.feature.paging.PagingData
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain

class GetFromCacheOrRemoteUseCaseImpl : GetFromCacheOrRemoteUseCase<PagingData<ItemDomain>> {

    override suspend fun invoke(
        isRefresh: Boolean,
        getFromRemoteCall: suspend () -> PagingData<ItemDomain>,
        onGetFromRemoteCallSuccess: suspend (PagingData<ItemDomain>) -> Unit,
        getFromCacheCall: suspend () -> PagingData<ItemDomain>?
    ): PagingData<ItemDomain>? {
        return if (isRefresh) {
            val returnRemoteCall = getFromRemoteCall.invoke()
            onGetFromRemoteCallSuccess.invoke(returnRemoteCall)
            returnRemoteCall
        } else {
            getFromCacheCall.invoke()
        }
    }
}