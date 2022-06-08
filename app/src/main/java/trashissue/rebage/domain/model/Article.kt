package trashissue.rebage.domain.model

import java.util.*

data class Article(
    val id: Int,
    val author: String,
    val source: String,
    val title: String,
    val body: String,
    val photo: List<String>,
    val createdAt: Date,
    val updatedAt: Date
)
