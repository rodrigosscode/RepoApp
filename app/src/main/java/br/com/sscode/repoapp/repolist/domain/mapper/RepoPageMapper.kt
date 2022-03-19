package br.com.sscode.repoapp.repolist.domain.mapper

import br.com.sscode.repoapp.repolist.data.entity.RepoPageResponse
import br.com.sscode.repoapp.repolist.domain.entity.RepoPageDomain

object RepoPageMapper {

    fun convertRepoPageResponseToDomain(repoPageResponse: RepoPageResponse): RepoPageDomain {
        return RepoPageDomain(
            incompleteResults = repoPageResponse.incompleteResults,
            items = convertItemsResponseToDomain(repoPageResponse.items),
            totalCount = repoPageResponse.totalCount
        )
    }

    fun convertItemsResponseToDomain(itemsResponse: List<RepoPageResponse.Item>): List<RepoPageDomain.Item> {
        return itemsResponse.map {
            RepoPageDomain.Item(
                forksCount = it.forksCount,
                name = it.name,
                owner = convertOwnerResponseToDomain(it.owner),
                stargazersCount = it.stargazersCount
            )
        }
    }

    fun convertOwnerResponseToDomain(ownerResponse: RepoPageResponse.Item.Owner): RepoPageDomain.Item.Owner {
        return RepoPageDomain.Item.Owner(
            avatarUrl = ownerResponse.avatarUrl,
            login = ownerResponse.login
        )
    }

    fun convertItemResponseToDomain(itemResponse: RepoPageResponse.Item): RepoPageDomain.Item {
        return RepoPageDomain.Item(
            itemResponse.forksCount,
            itemResponse.name,
            convertOwnerResponseToDomain(itemResponse.owner),
            itemResponse.stargazersCount
        )
    }
}