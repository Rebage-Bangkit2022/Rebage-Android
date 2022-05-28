package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<User?> {
        return userRepository.getUser()
    }
}
