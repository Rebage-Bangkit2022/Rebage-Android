package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.domain.repository.DetectionRepository
import trashissue.rebage.domain.repository.UserRepository

class GetDetectionsStatisticUseCase(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
) {

    suspend operator fun invoke(): Result<List<DetectionStatistic>> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        detectionRepository.getDetectionsStatistic(user.token)
    }
}
