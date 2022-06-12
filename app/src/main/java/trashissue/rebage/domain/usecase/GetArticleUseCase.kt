package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository
import trashissue.rebage.domain.repository.UserRepository

class GetArticleUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(articleId: Int): Result<Article> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        articleRepository.getArticle(user.token, articleId)
    }
}
