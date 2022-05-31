package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.GarbageRemoteDataSource
import trashissue.rebage.domain.model.DetectedGarbage
import trashissue.rebage.domain.repository.GarbageRepository
import java.io.File

class DefaultGarbageRepository(
    private val garbageRemoteDataSource: GarbageRemoteDataSource
) : GarbageRepository {

    override suspend fun detect(file: File): DetectedGarbage {
        val res = garbageRemoteDataSource.detect(file)
        return res.asModel()
    }
}
