package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import trashissue.rebage.BuildConfig
import trashissue.rebage.data.remote.payload.NearbySearchResponse

interface GoogleMapService {

    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=${BuildConfig.GOOGLE_MAPS_WEB_KEY}")
    suspend fun getNearby(
        @Query("location")
        location: String?,
        @Query("radius")
        radius: Double?,
        @Query("type")
        type: String?,
        @Query("keyword")
        keyword: String?
    ): Response<NearbySearchResponse>
}
