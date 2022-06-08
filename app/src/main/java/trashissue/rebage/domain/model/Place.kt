package trashissue.rebage.domain.model

data class Place(
    val id: String,
    val lat: Double,
    val lng: Double,
    val name: String,
    val rating: Float,
    val vicinity: String,
    val phoneNumber: String?,
    val businessStatus: String
)
