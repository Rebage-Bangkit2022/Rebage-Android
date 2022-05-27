package trashissue.rebage.data.remote.payload

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String?
)
