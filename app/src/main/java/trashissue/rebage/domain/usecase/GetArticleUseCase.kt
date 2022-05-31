package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository

class GetArticleUseCase(
    private val articleRepository: ArticleRepository
) {

    operator fun invoke(category: String? = null, page: Int = 1, size: Int = 20): Flow<List<Article>> {
        return articleRepository.getArticles(category, page, size)
    }
}
