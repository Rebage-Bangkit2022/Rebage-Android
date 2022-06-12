package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName
import java.util.*

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
    @field:SerializedName("liked")
    val liked: Boolean = false,
    @field:SerializedName("createdAt")
    val createdAt: Date,
    @field:SerializedName("updatedAt")
    val updatedAt: Date
)
