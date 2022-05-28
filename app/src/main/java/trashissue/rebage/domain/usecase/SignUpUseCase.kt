package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) : FlowUseCase<Result<User>>(Result.NoData()) {

    suspend operator fun invoke(name: String, email: String, password: String) {
        emit(Result.NoData(loading = true))
        try {
            val user = userRepository.signUp(name, email, password)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
