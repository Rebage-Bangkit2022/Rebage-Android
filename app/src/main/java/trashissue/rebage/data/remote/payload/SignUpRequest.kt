package trashissue.rebage.data.remote.payload

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)
