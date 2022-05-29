package trashissue.rebage.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.isLoading
import trashissue.rebage.domain.usecase.AuthGoogleUseCase
import trashissue.rebage.domain.usecase.SignInUseCase
import trashissue.rebage.domain.usecase.ValidateEmailUseCase
import trashissue.rebage.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signInUseCase: SignInUseCase,
    private val authGoogleUseCase: AuthGoogleUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()
    val signInResult = signInUseCase.result
    val isEnabled = combine(formState, signInResult) { (_formState, _signInResult) ->
        val formState = _formState as FormState
        val signInResult = _signInResult as Result<*>
        formState.isNotBlank && formState.isNotError && !signInResult.isLoading
    }
        .distinctUntilChanged()
        .catch { emit(false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun onEmailChange(email: String) = viewModelScope.launch {
        val errorMessage = validateEmailUseCase(email)
        val formState = formState.value.copy(
            email = email,
            emailErrorMessage = errorMessage
        )
        _formState.emit(formState)
    }

    fun onPasswordChange(password: String) {
        viewModelScope.launch {
            val errorMessage = validatePasswordUseCase(password)
            val formState =
                formState.value.copy(
                    password = password,
                    passwordErrorMessage = errorMessage
                )
            _formState.emit(formState)
        }
    }

    fun signIn() {
        viewModelScope.launch(dispatcher) {
            if (!isEnabled.value) return@launch
            val formState = formState.value
            signInUseCase(formState.email, formState.password)
        }
    }

    fun authGoogle(idToken: String) {
        viewModelScope.launch(dispatcher) {
            authGoogleUseCase(idToken)
        }
    }
}

