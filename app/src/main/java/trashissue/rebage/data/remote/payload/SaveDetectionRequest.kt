package trashissue.rebage.data.remote.payload

data class SaveDetectionRequest(
    val image: String,
    val label: String,
    val total: Int
)
