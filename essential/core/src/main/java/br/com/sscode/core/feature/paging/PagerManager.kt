package br.com.sscode.core.feature.paging

data class PagerManager(
    val currentPage: Int,
    val prevPage: Int,
    val nextPage: Int,
) {
    companion object {
        fun build(currentPage: Int): PagerManager = PagerManager(
            currentPage = currentPage,
            prevPage = if(currentPage > FIRST_PAGE) currentPage.minus(1) else currentPage,
            nextPage = currentPage.plus(1)
        )

        const val FIRST_PAGE = 1
    }
}


