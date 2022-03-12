package br.com.sscode.repoapp.repolist.domain.entity

data class RepoPageDomain(
    val incompleteResults: Boolean,
    val items: List<Item>,
    val totalCount: Int
) {
    data class Item(
        val forksCount: Int,
        val name: String,
        val owner: Owner,
        val stargazersCount: Int
    ) {
        data class Owner(
            val avatarUrl: String,
            val login: String
        )
    }
}

