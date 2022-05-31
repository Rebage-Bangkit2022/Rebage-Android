package trashissue.rebage.data.mapper

import trashissue.rebage.data.remote.payload.DetectedGarbageResponse
import trashissue.rebage.domain.model.DetectedGarbage

fun DetectedGarbageResponse.asModel(): DetectedGarbage {
    val detected = mutableListOf<DetectedGarbage.Detected>()
    val size = minOf(result.boundingBoxes.size, result.labels.size, result.scores.size)

    for (i in 0 until size) {
        val boundingBox = result.boundingBoxes[i]
        val label = result.labels[i]
        val score = result.scores[i]
        detected.add(DetectedGarbage.Detected(
            label = label,
            boundingBox = boundingBox,
            score = score
        ))
    }

    return DetectedGarbage(
        image = image,
        detected = detected
    )
}
