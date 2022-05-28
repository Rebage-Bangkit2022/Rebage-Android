package trashissue.rebage.data.local.entity

data class UserEntity(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String?,
    val token: String
)
