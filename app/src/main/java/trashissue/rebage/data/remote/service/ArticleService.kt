package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import trashissue.rebage.data.remote.payload.Api
import trashissue.rebage.data.remote.payload.ArticleResponse

interface ArticleService {

    @GET("/api/articles")
    suspend fun getArticles(
        @Query("category")
        category: String?,
        @Query("garbagecategory")
        garbageCategory: String?,
        @Query("page")
        page: Int?,
        @Query("size")
        size: Int?
    ): Response<Api<List<ArticleResponse>>>

    @GET("/api/article/{id}")
    suspend fun getArticle(
        @Path("id")
        id: Int
    ): Response<Api<ArticleResponse>>
}
