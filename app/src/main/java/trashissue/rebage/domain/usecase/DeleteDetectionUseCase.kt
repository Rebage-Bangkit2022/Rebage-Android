package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.repository.DetectionRepository
import trashissue.rebage.domain.repository.UserRepository

class DeleteDetectionUseCase(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
) {

    suspend operator fun invoke(id: Int): Result<Detection> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        detectionRepository.delete(user.token, id)
    }
}
