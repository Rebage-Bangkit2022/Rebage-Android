package trashissue.rebage.domain.repository

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.DetectionStatistic
import java.io.File

interface DetectionRepository {

    suspend fun save(token: String, image: String, label: String, total: Int): Detection

    suspend fun detect(token: String, file: File): List<Detection>

    suspend fun getDetections(token: String): Flow<List<Detection>>

    suspend fun getDetection(token: String, detectionId: Int): Detection

    suspend fun getDetectionsStatistic(token: String): List<DetectionStatistic>

    suspend fun update(token: String, detectionId: Int, total: Int): Detection

    suspend fun delete(token: String, detectionId: Int): Detection
}
