package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.ArticleRemoteDataSource
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.repository.ArticleRepository

class DefaultArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article> {
        return articleRemoteDataSource.getArticles(category, garbageCategory, page, size)
            .map { it.asModel() }
    }

    override suspend fun getArticle(articleId: Int): Article {
        return articleRemoteDataSource.getArticle(articleId).asModel()
    }
}
