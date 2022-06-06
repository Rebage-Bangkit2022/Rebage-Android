package trashissue.rebage.domain.model

import com.google.gson.annotations.SerializedName

data class DetectionStatistic(
    @field:SerializedName("label")
    val label: String,
    @field:SerializedName("total")
    val total: Int
)
