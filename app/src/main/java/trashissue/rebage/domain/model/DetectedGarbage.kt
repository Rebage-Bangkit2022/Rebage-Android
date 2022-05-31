package trashissue.rebage.domain.model

data class DetectedGarbage(
    val image: String,
    val detected: List<Detected>
) {

    data class Detected(
        val label: String,
        val boundingBox: List<Float>,
        val score: Float
    )
}
