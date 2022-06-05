package trashissue.rebage.data.remote.payload

import com.google.gson.annotations.SerializedName

data class GarbageResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("price")
    val price: Int,
    @field:SerializedName("image")
    val image: String
)
