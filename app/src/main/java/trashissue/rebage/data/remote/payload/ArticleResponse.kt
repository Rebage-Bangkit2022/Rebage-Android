package trashissue.rebage.data.remote.payload

data class ArticleResponse(
    val id: Int,
    val author: String,
    val source: String,
    val title: String,
    val body: String,
    val photo: List<String>,
    val createdAt: String,
    val updatedAt: String
)
