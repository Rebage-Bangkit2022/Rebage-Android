package trashissue.rebage.data.mapper

import trashissue.rebage.data.remote.payload.DetectionResponse
import trashissue.rebage.domain.model.Detection

fun DetectionResponse.asModel(): Detection {
    return Detection(
        id = id,
        image = image,
        label = label,
        boundingBoxes = boundingBoxes,
        scores = scores,
        total = total,
        createdAt = createdAt
    )
}
