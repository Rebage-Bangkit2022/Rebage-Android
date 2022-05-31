package trashissue.rebage.data.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import trashissue.rebage.data.remote.payload.DetectedGarbageResponse
import trashissue.rebage.data.remote.service.GarbageService
import java.io.File

class GarbageRemoteDataSource(
    private val garbageService: GarbageService
) {

    suspend fun detect(file: File): DetectedGarbageResponse {
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val rbFile = MultipartBody.Part.createFormData("image", file.name, requestImageFile)
        val res = garbageService.detect(rbFile)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
