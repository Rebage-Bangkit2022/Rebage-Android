package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Place

interface GoogleMapsRepository {

    suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double,
        type: String,
        keyword: String
    ): List<Place>

    suspend fun getPlace(placeId: String): Place
}
