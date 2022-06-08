package trashissue.rebage.domain.model

import java.util.*

data class Detection(
    val id: Int,
    val image: String,
    val label: String,
    val boundingBoxes: List<List<Float>>,
    val scores: List<Float>,
    val total: Int,
    val createdAt: Date
)

