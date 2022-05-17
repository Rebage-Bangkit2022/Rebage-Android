package trashissue.rebage.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.SignInUseCase
import trashissue.rebage.domain.usecase.ValidateEmailUseCase
import trashissue.rebage.domain.usecase.ValidatePasswordUseCase
import trashissue.rebage.presentation.common.FieldState
import trashissue.rebage.presentation.common.isError
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private var _emailValue = MutableStateFlow(FieldState())
    var emailValue = _emailValue.asStateFlow()
    private var _passwordValue = MutableStateFlow(FieldState())
    var passwordValue = _passwordValue.asStateFlow()
    val isEnabled = combine(emailValue, passwordValue, ::isNotError)
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val signInResult = signInUseCase.result

    private fun isNotError(email: FieldState, password: FieldState): Boolean {
        return !email.isError && !password.isError && email.value.isNotBlank() && password.value.isNotBlank()
    }

    fun validateEmail(email: String) {
        val message = validateEmailUseCase(email)
        _emailValue.value = emailValue.value.copy(
            value = email,
            errorMessage = message
        )
    }

    fun validatePassword(password: String) {
        val message = validatePasswordUseCase(password)
        _passwordValue.value = passwordValue.value.copy(
            value = password,
            errorMessage = message
        )
    }

    fun signIn() = viewModelScope.launch {
        if (!isEnabled.value) return@launch
        signInUseCase(emailValue.value.value, passwordValue.value.value)
    }
}
