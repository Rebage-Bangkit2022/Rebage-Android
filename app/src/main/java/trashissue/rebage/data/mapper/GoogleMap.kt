package trashissue.rebage.data.mapper

import trashissue.rebage.data.remote.payload.GoogleMapResponse
import trashissue.rebage.domain.model.GarbageBank

fun GoogleMapResponse.Result.asModel(): GarbageBank {
    return GarbageBank(
        id = placeId,
        name = name,
        rating = rating,
        vicinity = vicinity,
        lat = geometry.location.lat,
        lng = geometry.location.lng
    )
}
