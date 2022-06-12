package trashissue.rebage.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import trashissue.rebage.data.local.DetectionLocalDataSource
import trashissue.rebage.data.mapper.asEntity
import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.DetectionRemoteDataSource
import trashissue.rebage.data.remote.payload.SaveDetectionRequest
import trashissue.rebage.data.remote.payload.UpdateDetectionRequest
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.domain.repository.DetectionRepository
import java.io.File

class DefaultDetectionRepository(
    private val detectionLocalDataSource: DetectionLocalDataSource,
    private val detectionRemoteDataSource: DetectionRemoteDataSource
) : DetectionRepository {

    override suspend fun save(token: String, image: String, label: String, total: Int): Detection {
        val req = SaveDetectionRequest(
            image = image,
            label = label,
            total = total
        )
        val res = detectionRemoteDataSource.save(token, req)
        detectionLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun detect(token: String, file: File): List<Detection> {
        val res = detectionRemoteDataSource.detect(token, file)
        detectionLocalDataSource.save(res.map { it.asEntity() })
        return res.map { it.asModel() }
    }

    override suspend fun getDetections(token: String): Flow<List<Detection>> {
        val res = detectionRemoteDataSource.getDetections(token)
        detectionLocalDataSource.delete()
        detectionLocalDataSource.save(res.map { it.asEntity() })
        return detectionLocalDataSource
            .getDetections()
            .map { it.map { entity -> entity.asModel() } }
    }

    override suspend fun getDetection(token: String, detectionId: Int): Detection {
        val res = detectionRemoteDataSource.getDetection(token, detectionId)
        return res.asModel()
    }

    override suspend fun getDetectionsStatistic(token: String): List<DetectionStatistic> {
        val res = detectionRemoteDataSource.getDetectionsStatistic(token)
        return res.map { it.asModel() }
    }

    override suspend fun update(token: String, detectionId: Int, total: Int): Detection {
        val req = UpdateDetectionRequest(id = detectionId, total = total)
        val res = detectionRemoteDataSource.update(token, req)
        detectionLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun delete(token: String, detectionId: Int): Detection {
        val res = detectionRemoteDataSource.delete(token, detectionId)
        detectionLocalDataSource.delete(res.asEntity())
        return res.asModel()
    }
}
