package trashissue.rebage.data.remote.payload


data class Api<T>(
    val success: Boolean,
    val data: T
)
