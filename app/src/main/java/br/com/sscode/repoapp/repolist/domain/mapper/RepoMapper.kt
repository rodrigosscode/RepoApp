package br.com.sscode.repoapp.repolist.domain.mapper

import br.com.sscode.repoapp.repolist.data.entity.RepoResponse
import br.com.sscode.repoapp.repolist.domain.entity.RepoDomain

object RepoMapper {

    fun convertResponseToDomain(repoResponse: RepoResponse): RepoDomain {
        return RepoDomain(
            incompleteResults = repoResponse.incompleteResults,
            items = convertItemsResponseToDomain(repoResponse.items),
            totalCount = repoResponse.totalCount
        )
    }

    private fun convertItemsResponseToDomain(itemsResponse: List<RepoResponse.Item>?): List<RepoDomain.Item> {
        return itemsResponse?.map {
            RepoDomain.Item(
                forksCount = it.forksCount,
                name = it.name,
                owner = convertOwnerResponseToDomain(it.owner),
                stargazersCount = it.stargazersCount
            )
        } ?: emptyList()
    }

    private fun convertOwnerResponseToDomain(ownerResponse: RepoResponse.Item.Owner): RepoDomain.Item.Owner {
        return RepoDomain.Item.Owner(
            avatarUrl = ownerResponse.avatarUrl,
            login = ownerResponse.login
        )
    }
}