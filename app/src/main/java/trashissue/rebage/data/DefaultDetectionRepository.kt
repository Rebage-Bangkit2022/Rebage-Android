package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.DetectionRemoteDataSource
import trashissue.rebage.data.remote.payload.UpdateDetectionRequest
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.domain.repository.DetectionRepository
import java.io.File

class DefaultDetectionRepository(
    private val detectionRemoteDataSource: DetectionRemoteDataSource
) : DetectionRepository {

    override suspend fun detect(token: String, file: File): List<Detection> {
        val res = detectionRemoteDataSource.detect(token, file)
        return res.map { it.asModel() }
    }

    override suspend fun getDetections(token: String): List<Detection> {
        val res = detectionRemoteDataSource.getDetections(token)
        return res.map { it.asModel() }
    }

    override suspend fun getDetection(token: String, id: Int): Detection {
        val res = detectionRemoteDataSource.getDetection(token, id)
        return res.asModel()
    }

    override suspend fun getDetectionsStatistic(token: String): List<DetectionStatistic> {
        val res = detectionRemoteDataSource.getDetectionsStatistic(token)
        return res.map { it.asModel() }
    }

    override suspend fun update(token: String, id: Int, total: Int): Detection {
        val req = UpdateDetectionRequest(id = id, total = total)
        val res = detectionRemoteDataSource.update(token, req)
        return res.asModel()
    }

    override suspend fun delete(token: String, id: Int): Detection {
        val res = detectionRemoteDataSource.delete(token, id)
        return res.asModel()
    }
}
