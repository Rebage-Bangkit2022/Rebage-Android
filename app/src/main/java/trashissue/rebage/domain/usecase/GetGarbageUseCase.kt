package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.domain.repository.GarbageRepository

class GetGarbageUseCase(
    private val garbageRepository: GarbageRepository
) {

    suspend operator fun invoke(name: String): Result<Garbage> = runCatching {
        garbageRepository.getGarbage(name)
    }

    suspend operator fun invoke(): Result<List<Garbage>> = runCatching {
        garbageRepository.getGarbage()
    }
}
