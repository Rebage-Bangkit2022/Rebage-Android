package trashissue.rebage.data.mapper

import trashissue.rebage.data.local.entity.DetectionEntity
import trashissue.rebage.data.remote.payload.DetectionResponse
import trashissue.rebage.data.remote.payload.DetectionStatisticResponse
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.DetectionStatistic

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

fun DetectionStatisticResponse.asModel(): DetectionStatistic {
    return DetectionStatistic(
        label = label,
        total = total
    )
}

fun DetectionResponse.asEntity(): DetectionEntity {
    return DetectionEntity(
        id = id,
        image = image,
        label = label,
        boundingBoxes = boundingBoxes,
        scores = scores,
        total = total,
        createdAt = createdAt
    )
}

fun DetectionEntity.asModel(): Detection {
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
