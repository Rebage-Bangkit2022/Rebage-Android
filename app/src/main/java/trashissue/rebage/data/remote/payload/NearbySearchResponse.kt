package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class NearbySearchResponse(
    @field:SerializedName("results")
    val places: List<Place>
) {

    data class Place(
        @field:SerializedName("place_id")
        val placeId: String,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("business_status")
        val businessStatus: String,
        @field:SerializedName("geometry")
        val geometry: Geometry,
        @field:SerializedName("permanently_closed")
        val permanentlyClosed: Boolean,
        @field:SerializedName("rating")
        val rating: Float,
        @field:SerializedName("reference")
        val reference: String,
        @field:SerializedName("user_ratings_total")
        val userRatingsTotal: Int,
        @field:SerializedName("vicinity")
        val vicinity: String
    )

    data class Geometry(
        @field:SerializedName("location")
        val location: Location
    ) {

        data class Location(
            @field:SerializedName("lat")
            val lat: Double,
            @field:SerializedName("lng")
            val lng: Double
        )
    }
}
