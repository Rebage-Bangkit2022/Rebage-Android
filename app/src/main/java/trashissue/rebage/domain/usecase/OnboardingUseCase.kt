package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.repository.UserRepository

class OnboardingUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return userRepository.onboarding()
    }

    suspend operator fun invoke(isAlreadyOnboarding: Boolean) {
        userRepository.onboarding(isAlreadyOnboarding)
    }
}
