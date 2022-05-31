package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.DetectedGarbage
import java.io.File

interface GarbageRepository {

    suspend fun detect(file: File): DetectedGarbage
}
