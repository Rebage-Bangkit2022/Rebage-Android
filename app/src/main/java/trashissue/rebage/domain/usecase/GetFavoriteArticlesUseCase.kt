package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository
import trashissue.rebage.domain.repository.UserRepository

class GetFavoriteArticlesUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(): Result<List<Article>> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        articleRepository.getFavoriteArticle(user.token)
    }
}
