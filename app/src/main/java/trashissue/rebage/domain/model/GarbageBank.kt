package trashissue.rebage.domain.model

data class GarbageBank(
    val id: String,
    val name: String,
    val rating: Double,
    val vicinity: String,
    val lat: Double,
    val lng: Double
)
