package trashissue.rebage.data.remote.payload

data class TokenResponse(
    val token: String,
    val user: UserResponse
)
