package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class EditUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        password: String,
        photo: String? = null
    ): Result<User> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        userRepository.edit(user.token, name, password, photo)
    }
}
