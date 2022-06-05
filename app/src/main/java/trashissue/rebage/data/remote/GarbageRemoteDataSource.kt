package trashissue.rebage.data.remote

import timber.log.Timber
import trashissue.rebage.data.remote.payload.GarbageResponse
import trashissue.rebage.data.remote.service.GarbageService

class GarbageRemoteDataSource(
    private val garbageService: GarbageService
) {

    suspend fun getGarbage(name: String): GarbageResponse {
        val res = garbageService.getGarbage(name)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getGarbage(): List<GarbageResponse> {
        val res = garbageService.getGarbage()
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
