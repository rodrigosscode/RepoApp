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

    private fun convertItemsResponseToDomain(itemsResponse: List<RepoPageResponse.Item>): List<RepoPageDomain.Item> {
        return itemsResponse.map {
            RepoPageDomain.Item(
                forksCount = it.forksCount,
                name = it.name,
                owner = convertOwnerResponseToDomain(it.owner),
                stargazersCount = it.stargazersCount
            )
        }
    }

    private fun convertOwnerResponseToDomain(ownerResponse: RepoPageResponse.Item.Owner): RepoPageDomain.Item.Owner {
        return RepoPageDomain.Item.Owner(
            avatarUrl = ownerResponse.avatarUrl,
            login = ownerResponse.login
        )
    }
}