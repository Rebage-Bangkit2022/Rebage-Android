package trashissue.rebage.data.remote.service

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import trashissue.rebage.data.remote.payload.*

interface DetectionService {

    @POST("/api/detection")
    suspend fun save(
        @Header("Authorization")
        token: String,
        @Body
        req: SaveDetectionRequest
    ): Response<Api<DetectionResponse>>

    @POST("/api/detection/detect")
    @Multipart
    suspend fun detect(
        @Header("Authorization")
        token: String,
        @Part
        image: MultipartBody.Part
    ): Response<Api<List<DetectionResponse>>>

    @GET("/api/detections")
    suspend fun getDetections(
        @Header("Authorization")
        token: String,
    ): Response<Api<List<DetectionResponse>>>

    @GET("/api/detection/{id}")
    suspend fun getDetection(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int
    ): Response<Api<DetectionResponse>>

    @GET("/api/detections/stats")
    suspend fun getDetectionsStatistic(
        @Header("Authorization")
        token: String
    ): Response<Api<List<DetectionStatisticResponse>>>

    @PUT("/api/detection/{id}")
    suspend fun update(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int,
        @Body
        req: UpdateDetectionRequest
    ): Response<Api<DetectionResponse>>

    @DELETE("/api/detection/{id}")
    suspend fun delete(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int
    ): Response<Api<DetectionResponse>>
}
