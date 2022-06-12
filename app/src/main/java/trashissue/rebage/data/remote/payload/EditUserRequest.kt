package trashissue.rebage.data.remote.payload

data class EditUserRequest(
    val name: String,
    val password: String,
    val photo: String?
)
