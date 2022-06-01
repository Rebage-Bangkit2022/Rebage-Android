package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.repository.ArticleRepository

class GetArticleUseCase(
    private val articleRepository: ArticleRepository
) : FlowUseCase<Result<Article>>(Result.Empty) {

    suspend operator fun invoke(articleId: Int) {
        try {
            emit(Result.Loading)
            val article = articleRepository.getArticle(articleId)
            emit(Result.Success(article))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
