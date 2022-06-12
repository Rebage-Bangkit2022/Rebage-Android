package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Article

interface ArticleRepository {

    suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article>

    suspend fun getArticle(token: String, articleId: Int): Article

    suspend fun getFavoriteArticle(token: String): List<Article>

    suspend fun like(token: String, articleId: Int): Article

    suspend fun unlike(token: String, articleId: Int): Article
}
