package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("author")
    val author: String,
    @field:SerializedName("source")
    val source: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("body")
    val body: String,
    @field:SerializedName("photo")
    val photo: List<String>,
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String
)
