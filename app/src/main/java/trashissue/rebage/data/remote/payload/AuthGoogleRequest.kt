package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class AuthGoogleRequest(
    @field:SerializedName("idToken")
    val idToken: String
)
