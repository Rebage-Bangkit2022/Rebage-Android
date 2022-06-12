package trashissue.rebage.data.local

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.data.local.entity.DetectionEntity
import trashissue.rebage.data.local.room.DetectionDao

class DetectionLocalDataSource(
    private val detectionDao: DetectionDao
) {

    suspend fun save(detection: DetectionEntity) {
        detectionDao.save(detection)
    }

    suspend fun save(detections: List<DetectionEntity>) {
        detectionDao.save(detections)
    }

    fun getDetections(): Flow<List<DetectionEntity>> {
        return detectionDao.getDetections()
    }

    suspend fun delete(detectionEntity: DetectionEntity) {
        detectionDao.delete(detectionEntity)
    }

    suspend fun delete() {
        detectionDao.delete()
    }
}
