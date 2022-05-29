package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class AuthGoogleUseCase(
    private val userRepository: UserRepository
) : FlowUseCase<Result<User>>(Result.NoData()) {

    suspend operator fun invoke(idToken: String) {
        emit(Result.NoData(loading = true))
        try {
            val user = userRepository.authGoogle(idToken)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
