package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.Garbage

interface GarbageRepository {

    suspend fun getGarbage(name: String): Garbage
}
