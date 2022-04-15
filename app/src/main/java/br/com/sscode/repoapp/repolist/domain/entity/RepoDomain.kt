package br.com.sscode.repoapp.repolist.domain.entity

data class RepoDomain(
    val incompleteResults: Boolean,
    val items: List<ItemDomain>,
    val totalCount: Int
)

data class ItemDomain(
    val forksCount: Int,
    val name: String,
    val owner: OwnerDomain,
    val stargazersCount: Int
)

data class OwnerDomain(
    val avatarUrl: String,
    val login: String
)

