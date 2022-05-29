package trashissue.rebage.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.isLoading
import trashissue.rebage.domain.usecase.SignUpUseCase
import trashissue.rebage.domain.usecase.ValidateEmailUseCase
import trashissue.rebage.domain.usecase.ValidateNameUseCase
import trashissue.rebage.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()
    val signUpResult = signUpUseCase.result
    val isEnabled = combine(formState, signUpResult) { (_formState, _signUpResult) ->
        val formState = _formState as FormState
        val signUpResult = _signUpResult as Result<*>
        formState.isNotBlank && formState.isNotError && !signUpResult.isLoading
    }
        .distinctUntilChanged()
        .catch { emit(false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    fun onNameChange(name: String) {
        viewModelScope.launch {
            val errorMessage = validateNameUseCase(name)
            val formState = formState.value.copy(
                name = name,
                nameErrorMessage = errorMessage
            )
            _formState.emit(formState)
        }
    }

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

    fun onConfirmPasswordChange(confirm: String) {
        viewModelScope.launch {
            val errorMessage = validatePasswordUseCase(formState.value.password, confirm)
            val formState = formState.value.copy(
                confirmPassword = confirm,
                confirmPasswordErrorMessage = errorMessage
            )
            _formState.emit(formState)
        }
    }

    fun signUp() {
        viewModelScope.launch(dispatcher) {
            if (!isEnabled.value) return@launch
            val formState = formState.value
            Timber.i("${formState.name}, ${formState.email}, ${formState.password}")
            signUpUseCase(formState.name, formState.email, formState.password)
        }
    }
}
