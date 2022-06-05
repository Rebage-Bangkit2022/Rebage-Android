package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class DetectionStatisticResponse(
    @field:SerializedName("label")
    val label: String,
    @field:SerializedName("total")
    val total: Int
)
