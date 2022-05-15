package trashissue.rebage.data.local.entity

data class UserEntity(
    val id: Int,
    val photo: String,
    val name: String,
    val email: String,
    val token: String
)
