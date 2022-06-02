package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class Api<T>(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("data")
    val data: T
)
