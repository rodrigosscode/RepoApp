package br.com.sscode.repoapp.repolist.domain.mapper

import br.com.sscode.repoapp.repolist.data.entity.ItemResponse
import br.com.sscode.repoapp.repolist.data.entity.OwnerResponse
import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.domain.entity.ItemDomain
import br.com.sscode.repoapp.repolist.domain.entity.OwnerDomain
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

object RepoMapper {

    fun convertResponseToDomain(repoResponse: RepoResponse): RepoDomain {
        return RepoDomain(
            incompleteResults = repoResponse.incompleteResults ?: false,
            items = convertItemsResponseToDomain(repoResponse.items),
            totalCount = repoResponse.totalCount ?: -1
        )
    }

    private fun convertItemsResponseToDomain(itemsResponse: List<ItemResponse>?): List<ItemDomain> {
        return itemsResponse?.map {
            ItemDomain(
                forksCount = it.forksCount ?: -1,
                name = it.name ?: "",
                owner = convertOwnerResponseToDomain(it.owner),
                stargazersCount = it.stargazersCount ?: -1
            )
        } ?: emptyList()
    }

    private fun convertOwnerResponseToDomain(ownerResponse: OwnerResponse?): OwnerDomain {
        return OwnerDomain(
            avatarUrl = ownerResponse?.avatarUrl ?: "",
            login = ownerResponse?.login ?: ""
        )
    }
}