package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.DetectionStatistic
import java.io.File

interface DetectionRepository {

    suspend fun detect(token: String, file: File): List<Detection>

    suspend fun getDetections(token: String): List<Detection>

    suspend fun getDetection(token: String, id: Int): Detection

    suspend fun getDetectionsStatistic(token: String): List<DetectionStatistic>

    suspend fun update(token: String, id: Int, total: Int): Detection

    suspend fun delete(token: String, id: Int): Detection
}
