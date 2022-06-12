package trashissue.rebage.presentation.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.usecase.EditUserUseCase
import trashissue.rebage.domain.usecase.GetUserUseCase
import trashissue.rebage.domain.usecase.ValidateNameUseCase
import trashissue.rebage.domain.usecase.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val editUserUseCase: EditUserUseCase
) : ViewModel() {
    val user = getUserUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _nameField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val nameField = _nameField.asStateFlow()

    private val _passwordField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val passwordField = _passwordField.asStateFlow()

    private val _confirmPasswordField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val confirmPasswordField = _confirmPasswordField.asStateFlow()

    val fulfilled = combine(
        _nameField,
        _passwordField,
        _confirmPasswordField
    ) { (name, password, confirmPassword) ->
        name.first.isNotBlank()
                && name.second == null
                && password.first.isNotBlank()
                && password.second == null
                && confirmPassword.first.isNotBlank()
                && confirmPassword.second == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        viewModelScope.launch(dispatcher) {
            user.collectLatest { user ->
                if (user != null) {
                    _nameField.value = user.name to _nameField.value.second
                }
            }
        }
    }

    fun changeName(name: String) {
        val error = validateNameUseCase(name)
        _nameField.value = name to error
    }

    fun changePassword(password: String) {
        val error = validatePasswordUseCase(password)
        val error2 = validatePasswordUseCase(password, _confirmPasswordField.value.first)
        _passwordField.value = password to error
        _confirmPasswordField.value = _confirmPasswordField.value.first to error2
    }

    fun changeConfirmPassword(password: String) {
        val error2 = validatePasswordUseCase(_passwordField.value.first, password)
        _confirmPasswordField.value = password to error2
    }

    fun save() {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            if (fulfilled.value) {
                editUserUseCase(_nameField.value.first, _passwordField.value.first)
                    .onSuccess {
                        _snackbar.emit("Changes were successfully saved")
                    }
                    .onFailure { e ->
                        Timber.e(e)
                        _snackbar.emit("Unable to save changes")
                    }
            }
            _loading.value = false
        }
    }
}
