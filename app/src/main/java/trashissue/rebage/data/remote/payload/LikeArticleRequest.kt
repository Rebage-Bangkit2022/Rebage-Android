package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class LikeArticleRequest(
    @field:SerializedName("articleId")
    val articleId: Int,
    @field:SerializedName("userId")
    val userId: Int
)
