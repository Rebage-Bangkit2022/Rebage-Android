package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val articleRepository: ArticleRepository
) {

    operator fun invoke(category: String? = null, page: Int = 1, size: Int = 20): Flow<Result<List<Article>>> {
        return articleRepository
            .getArticles(category, page, size)
            .map { Result.Success(it) }
            .onStart { Result.Loading }
            .catch { Result.Error(it) }
    }
}
