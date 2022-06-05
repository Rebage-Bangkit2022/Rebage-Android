package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class SignInUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String, password: String): User {
        return userRepository.signIn(email, password)
    }
}
