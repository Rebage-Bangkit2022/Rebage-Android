package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("photo")
    val photo: String?,
    @field:SerializedName("token")
    val token: String
)
