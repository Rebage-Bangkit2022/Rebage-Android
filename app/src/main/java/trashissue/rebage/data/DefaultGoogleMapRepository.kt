package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.GoogleMapRemoteDataSource
import trashissue.rebage.domain.model.GarbageBank
import trashissue.rebage.domain.repository.GoogleMapRepository

class DefaultGoogleMapRepository(
    private val googleMapRemoteDataSource: GoogleMapRemoteDataSource
) : GoogleMapRepository {

    override suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double,
        type: String,
        keyword: String
    ): List<GarbageBank> {
        val res = googleMapRemoteDataSource.getNearby(lat, lng, radius, type, keyword)
        return res.map { it.asModel() }
    }
}
