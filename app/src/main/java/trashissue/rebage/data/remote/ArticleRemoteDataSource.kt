package trashissue.rebage.data.remote

import trashissue.rebage.data.remote.payload.ArticleResponse
import trashissue.rebage.data.remote.service.ArticleService

class ArticleRemoteDataSource(
    private val articleService: ArticleService
) {

    suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<ArticleResponse> {
        val res = articleService.getArticles(category, garbageCategory, page, size)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getArticle(token: String, articleId: Int): ArticleResponse {
        val res = articleService.getArticle("Bearer $token", articleId)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getFavoriteArticles(token: String): List<ArticleResponse> {
        val res = articleService.getFavoriteArticles("Bearer $token")
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun like(token: String, articleId: Int): ArticleResponse {
        val res = articleService.like("Bearer $token", articleId)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun unlike(token: String, articleId: Int): ArticleResponse {
        val res = articleService.unlike("Bearer $token", articleId)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
