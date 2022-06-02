package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class DetectedGarbageResponse(
    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("result")
    val result: DetectedGarbage
) {
    data class DetectedGarbage(
        @field:SerializedName("bounding_boxes")
        val boundingBoxes: List<List<Float>>,
        @field:SerializedName("label_detections")
        val labels: List<String>,
        @field:SerializedName("scores")
        val scores: List<Float>
    )
}
