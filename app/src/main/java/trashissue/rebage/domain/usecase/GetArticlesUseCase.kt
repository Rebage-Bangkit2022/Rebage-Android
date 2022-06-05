package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(
        category: String? = null,
        garbageCategory: String? = null,
        page: Int = 1,
        size: Int = 20
    ): Result<List<Article>> = runCatching {
        articleRepository.getArticles(category, garbageCategory, page, size)
    }
}
