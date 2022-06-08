package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Article

interface ArticleRepository {

    suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article>

    suspend fun getArticle(articleId: Int): Article

    suspend fun like(articleId: Int, userId: Int): Article

    suspend fun unlike(articleId: Int, userId: Int): Article
}
