package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() {
        userRepository.signOut()
    }
}
