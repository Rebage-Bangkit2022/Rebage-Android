package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class UpdateDetectionRequest(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("total")
    val total: Int
)
