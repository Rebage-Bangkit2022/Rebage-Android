package trashissue.rebage.data.mapper

import trashissue.rebage.data.remote.payload.ArticleResponse
import trashissue.rebage.domain.model.Article

fun ArticleResponse.asModel(): Article {
    return Article(
        id = id,
        author = author,
        source = source,
        title = title,
        body = body,
        photo = photo,
        liked = liked,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
