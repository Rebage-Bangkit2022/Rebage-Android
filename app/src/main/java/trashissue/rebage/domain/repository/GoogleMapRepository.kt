package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.GarbageBank

interface GoogleMapRepository {

    suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double,
        type: String,
        keyword: String
    ): List<GarbageBank>
}
