package br.com.sscode.core.feature.paging

data class PagingData<T>(
    val items: List<T>,
    val pageManager: PagerManager
)