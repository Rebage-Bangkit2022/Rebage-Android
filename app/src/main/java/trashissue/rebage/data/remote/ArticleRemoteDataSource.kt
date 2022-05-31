package trashissue.rebage.data.remote

import trashissue.rebage.data.remote.payload.ArticleResponse
import trashissue.rebage.data.remote.service.ArticleService

class ArticleRemoteDataSource(
    private val articleService: ArticleService
) {

    suspend fun getArticles(category: String?, page: Int, size: Int): List<ArticleResponse> {
        val res = articleService.getArticles(category, page, size)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
