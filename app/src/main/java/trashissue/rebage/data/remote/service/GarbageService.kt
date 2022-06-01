package trashissue.rebage.data.remote.service

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import trashissue.rebage.data.remote.payload.Api
import trashissue.rebage.data.remote.payload.DetectedGarbageResponse

interface GarbageService {

    @POST("/api/detection")
    @Multipart
    suspend fun detect(
        @Part image: MultipartBody.Part
    ): Response<Api<DetectedGarbageResponse>>
}
