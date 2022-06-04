package trashissue.rebage.data

import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.GarbageRemoteDataSource
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.domain.repository.GarbageRepository

class DefaultGarbageRepository(
    private val garbageRemoteDataSource: GarbageRemoteDataSource
) : GarbageRepository {

    override suspend fun getGarbage(name: String): Garbage {
        val res = garbageRemoteDataSource.getGarbage(name)
        return res.asModel()
    }
}
