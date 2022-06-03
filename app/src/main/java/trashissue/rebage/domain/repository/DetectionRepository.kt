package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Detection
import java.io.File

interface DetectionRepository {

    suspend fun detect(token: String, file: File): List<Detection>

    suspend fun getDetectedGarbage(token: String): List<Detection>

    suspend fun getDetectedGarbage(token: String, id: Int): Detection

    suspend fun update(token: String, id: Int, total: Int): Detection

    suspend fun delete(token: String, id: Int): Detection
}
