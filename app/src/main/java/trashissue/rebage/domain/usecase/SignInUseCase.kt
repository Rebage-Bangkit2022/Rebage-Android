package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    private val _result = MutableStateFlow<Result<User>>(Result.NoData())
    val result = _result.asStateFlow()

    suspend operator fun invoke(email: String, password: String) {
        _result.emit(Result.NoData())
        try {
            val user = authRepository.signIn(email, password)
            _result.emit(Result.Success(user))
        } catch (e: Exception) {
            _result.emit(Result.Error(e))
        }
    }
}