package trashissue.rebage.data.remote

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import trashissue.rebage.data.remote.payload.NearbySearchResponse
import trashissue.rebage.data.remote.payload.PlaceResponse
import trashissue.rebage.data.remote.service.GoogleMapService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class GoogleMapRemoteDataSource(
    private val googleMapService: GoogleMapService,
    private val placesClient: PlacesClient
) {

    suspend fun getNearby(
        lat: Double,
        lng: Double,
        radius: Double?,
        type: String?,
        keyword: String?
    ): List<NearbySearchResponse.Place> {
        val res = googleMapService.getNearby("$lat,$lng", radius, type, keyword)
        val data = res.takeIf { it.isSuccessful }?.body()?.places
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getPlace(placeId: String): PlaceResponse = suspendCoroutine { cont ->
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.RATING,
            Place.Field.ADDRESS,
            Place.Field.PHONE_NUMBER,
            Place.Field.LAT_LNG,
            Place.Field.BUSINESS_STATUS,
            Place.Field.OPENING_HOURS,
        )

        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val placeResponse = PlaceResponse(
                    id = response.place.id,
                    name = response.place.name,
                    rating = response.place.rating,
                    address = response.place.address,
                    phoneNumber = response.place.phoneNumber,
                    lat = response.place.latLng?.latitude,
                    lng = response.place.latLng?.longitude,
                    businessStatus = response.place.businessStatus?.name
                )
                cont.resume(placeResponse)
            }.addOnFailureListener { exception: Exception ->
                cont.resumeWithException(exception)
            }
    }
}
