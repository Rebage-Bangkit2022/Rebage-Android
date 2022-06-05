package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class DetectionResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("label")
    val label: String,
    @field:SerializedName("boundingBoxes")
    val boundingBoxes: List<List<Float>>,
    @field:SerializedName("scores")
    val scores: List<Float>,
    @field:SerializedName("total")
    val total: Int,
    @field:SerializedName("createdAt")
    val createdAt: String,
)
