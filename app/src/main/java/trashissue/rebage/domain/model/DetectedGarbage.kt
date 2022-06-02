package trashissue.rebage.domain.model

import java.util.*

data class DetectedGarbage(
    val image: String,
    val detected: List<Detected>
) {

    data class Detected(
        val label: String,
        val boundingBox: List<Float>,
        val score: Float
    )

    data class Group(
        val image: String,
        val label: String,
        val boundingBoxes: List<List<Float>>,
        val scores: List<Float>,
        val date: String,
        val total: Int
    )

    fun groupByLabel(): List<Group> {
        return detected
            .groupBy { it.label }
            .map { map ->
                Group(
                    image = image,
                    label = map.key,
                    boundingBoxes = map.value.map { it.boundingBox },
                    scores = map.value.map { it.score },
                    date = Date().toString(),
                    total = map.value.size
                )
            }
    }
}


