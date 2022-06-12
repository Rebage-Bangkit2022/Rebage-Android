package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.repository.UserRepository

class DarkThemeUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(isDarkTheme: Boolean?) {
        userRepository.darkTheme(isDarkTheme)
    }

    operator fun invoke(): Flow<Boolean?> {
        return userRepository.darkTheme()
    }
}
