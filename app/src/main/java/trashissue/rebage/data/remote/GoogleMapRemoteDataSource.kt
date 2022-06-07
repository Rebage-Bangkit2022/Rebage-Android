package trashissue.rebage.data.remote

import trashissue.rebage.data.remote.payload.GoogleMapResponse
import trashissue.rebage.data.remote.service.GoogleMapService

class GoogleMapRemoteDataSource(
    private val googleMapService: GoogleMapService
) {

    suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double?,
        type: String?,
        keyword: String?
    ): List<GoogleMapResponse.Result> {
        val res = googleMapService.getNearby("$lat,$lng", radius, type, keyword)
        val data = res.takeIf { it.isSuccessful }?.body()?.results
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
