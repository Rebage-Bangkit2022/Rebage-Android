package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.*
import trashissue.rebage.data.remote.payload.Api
import trashissue.rebage.data.remote.payload.ArticleResponse
import trashissue.rebage.data.remote.payload.LikeArticleRequest

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
        @Header("Authorization")
        token: String,
        @Path("id")
        articleId: Int
    ): Response<Api<ArticleResponse>>

    @GET("/api/article/user/like")
    suspend fun getFavoriteArticles(
        @Header("Authorization")
        token: String
    ): Response<Api<List<ArticleResponse>>>

    @POST("/api/article/{articleId}/like")
    suspend fun like(
        @Header("Authorization")
        token: String,
        @Path("articleId")
        articleId: Int
    ): Response<Api<ArticleResponse>>

    @DELETE("/api/article/{articleId}/unlike")
    suspend fun unlike(
        @Header("Authorization")
        token: String,
        @Path("articleId")
        articleId: Int
    ): Response<Api<ArticleResponse>>
}
