package trashissue.rebage.data.local

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.data.local.entity.DetectionEntity
import trashissue.rebage.data.local.room.DetectionDao

class DetectionLocalDataSource(
    private val detectionDao: DetectionDao
) {

    suspend fun saveDetection(detection: DetectionEntity) {
        detectionDao.saveDetection(detection)
    }

    suspend fun saveDetections(detections: List<DetectionEntity>) {
        detectionDao.saveDetections(detections)
    }

    fun getDetections(): Flow<List<DetectionEntity>> {
        return detectionDao.getDetections()
    }

    suspend fun deleteDetection(detectionEntity: DetectionEntity) {
        detectionDao.deleteDetection(detectionEntity)
    }
}
