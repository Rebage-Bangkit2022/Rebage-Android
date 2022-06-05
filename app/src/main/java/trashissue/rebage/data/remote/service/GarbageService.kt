package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import trashissue.rebage.data.remote.payload.Api
import trashissue.rebage.data.remote.payload.GarbageResponse

interface GarbageService {

    @GET("/api/garbage/name/{name}")
    suspend fun getGarbage(
        @Path("name")
        name: String
    ): Response<Api<GarbageResponse>>

    @GET("/api/garbages")
    suspend fun getGarbage(): Response<Api<List<GarbageResponse>>>
}
