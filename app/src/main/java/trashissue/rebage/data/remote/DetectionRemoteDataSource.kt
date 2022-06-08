package trashissue.rebage.data.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import trashissue.rebage.data.remote.payload.DetectionResponse
import trashissue.rebage.data.remote.payload.DetectionStatisticResponse
import trashissue.rebage.data.remote.payload.SaveDetectionRequest
import trashissue.rebage.data.remote.payload.UpdateDetectionRequest
import trashissue.rebage.data.remote.service.DetectionService
import java.io.File

class DetectionRemoteDataSource(
    private val detectionService: DetectionService
) {

    suspend fun save(token: String, req: SaveDetectionRequest): DetectionResponse {
        val res = detectionService.save("Bearer $token", req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun detect(token: String, file: File): List<DetectionResponse> {
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val rbFile = MultipartBody.Part.createFormData("image", file.name, requestImageFile)
        val res = detectionService.detect("Bearer $token", rbFile)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getDetections(token: String): List<DetectionResponse> {
        val res = detectionService.getDetections("Bearer $token")
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getDetection(token: String, id: Int): DetectionResponse {
        val res = detectionService.getDetection("Bearer $token", id)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getDetectionsStatistic(token: String): List<DetectionStatisticResponse> {
        val res = detectionService.getDetectionsStatistic("Bearer $token")
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun update(token: String, req: UpdateDetectionRequest): DetectionResponse {
        val res = detectionService.update("Bearer $token", req.id, req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun delete(token: String, id: Int): DetectionResponse {
        val res = detectionService.delete("Bearer $token", id)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
