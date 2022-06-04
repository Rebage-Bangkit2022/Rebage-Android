package trashissue.rebage.data.mapper

import trashissue.rebage.data.remote.payload.GarbageResponse
import trashissue.rebage.domain.model.Garbage

fun GarbageResponse.asModel(): Garbage {
    return Garbage(
        id = id,
        name = name,
        price = price,
        image = image
    )
}
