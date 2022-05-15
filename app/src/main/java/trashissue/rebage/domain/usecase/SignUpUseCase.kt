package trashissue.rebage.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    private val _result = MutableStateFlow<Result<User>>(Result.NoData(loading = false))
    val result = _result.asStateFlow()

    suspend operator fun invoke(name: String, email: String, password: String) {
        _result.emit(Result.NoData())
        try {
            val user = authRepository.signUp(name, email, password)
            _result.emit(Result.Success(user))
        } catch (e: Exception) {
            _result.emit(Result.Error(e))
        }
    }
}