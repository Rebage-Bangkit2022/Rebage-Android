package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository

class GetArticleUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(articleId: Int): Result<Article> = runCatching {
        articleRepository.getArticle(articleId)
    }
}
