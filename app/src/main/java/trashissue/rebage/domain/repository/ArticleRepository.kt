package trashissue.rebage.domain.repository

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.model.Article

interface ArticleRepository {

    fun getArticles(category: String? = null, page: Int, size: Int): Flow<List<Article>>
}
