package trashissue.rebage.domain.model

data class User(
    val id: Int,
    val photo: String,
    val name: String,
    val email: String,
    val token: String
)
