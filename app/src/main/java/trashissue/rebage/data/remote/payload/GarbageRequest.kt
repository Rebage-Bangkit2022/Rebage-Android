package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class GarbageRequest(
    @field:SerializedName("imageBase64")
    val imageBase64: String
)
