package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.repository.DetectionRepository
import trashissue.rebage.domain.repository.UserRepository
import java.io.File

class DetectGarbageUseCase(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
) {

    suspend operator fun invoke(file: File): Result<List<Detection>> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        detectionRepository.detect(user.token, file)
    }
}
