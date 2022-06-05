package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(name: String, email: String, password: String): User {
        return userRepository.signUp(name, email, password)
    }
}
