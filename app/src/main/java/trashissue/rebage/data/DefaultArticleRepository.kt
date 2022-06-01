package trashissue.rebage.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.ArticleRemoteDataSource
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository

class DefaultArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override fun getArticles(category: String?, page: Int, size: Int): Flow<List<Article>> {
        return flow {
            val res = articleRemoteDataSource.getArticles(category, page, size)
            emit(res.map { it.asModel() })
        }
    }

    override suspend fun getArticle(articleId: Int): Article{
        return articleRemoteDataSource.getArticle(articleId).asModel()
    }
}
