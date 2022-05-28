package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class SignInUseCase(
    private val userRepository: UserRepository
) : FlowUseCase<Result<User>>(Result.NoData()) {

    suspend operator fun invoke(email: String, password: String) {
        emit(Result.NoData(loading = true))
        try {
            val user = userRepository.signIn(email, password)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
