package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.GoogleMapRemoteDataSource
import trashissue.rebage.domain.model.Place
import trashissue.rebage.domain.repository.GoogleMapsRepository

class DefaultGoogleMapsRepository(
    private val googleMapRemoteDataSource: GoogleMapRemoteDataSource
) : GoogleMapsRepository {

    override suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double,
        type: String,
        keyword: String
    ): List<Place> {
        val res = googleMapRemoteDataSource.getNearby(lat, lng, radius, type, keyword)
        return res.map { it.asModel() }
    }

    override suspend fun getPlace(placeId: String): Place {
        return googleMapRemoteDataSource.getPlace(placeId).asModel()
    }
}
