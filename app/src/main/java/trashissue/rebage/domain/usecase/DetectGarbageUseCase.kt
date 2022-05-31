package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.DetectedGarbage
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.repository.GarbageRepository
import java.io.File

class DetectGarbageUseCase(
    private val garbageRepository: GarbageRepository
) : FlowUseCase<Result<DetectedGarbage>>(Result.Empty) {

    suspend operator fun invoke(file: File) {
        emit(Result.Loading)
        try {
            val detectedGarbage = garbageRepository.detect(file)
            emit(Result.Success(detectedGarbage))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
