package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class AuthGoogleUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(idToken: String): User {
        return userRepository.authGoogle(idToken)
    }
}
