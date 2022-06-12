package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository
import trashissue.rebage.domain.repository.UserRepository

class ToggleLikeArticleUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(articleId: Int, liked: Boolean = true): Result<Article> =
        runCatching {
            val user = userRepository
                .getUser()
                .firstOrNull() ?: throw RuntimeException("Unauthorized")
            if (liked) articleRepository.like(user.token, articleId)
            else articleRepository.unlike(user.token, articleId)
        }
}
