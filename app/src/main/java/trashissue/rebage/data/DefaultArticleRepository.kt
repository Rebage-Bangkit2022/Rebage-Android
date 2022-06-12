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
        val res = articleRemoteDataSource.getArticles(category, garbageCategory, page, size)
        return res.map { it.asModel() }
    }

    override suspend fun getArticle(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.getArticle(token, articleId)
        return res.asModel()
    }

    override suspend fun getFavoriteArticle(token: String): List<Article> {
        val res = articleRemoteDataSource.getFavoriteArticles(token)
        return res.map { it.asModel() }
    }

    override suspend fun like(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.like(token, articleId)
        return res.asModel()
    }

    override suspend fun unlike(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.unlike(token, articleId)
        return res.asModel()
    }
}
