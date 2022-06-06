package trashissue.rebage.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.usecase.GetUserUseCase
import trashissue.rebage.domain.usecase.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    val user = getUserUseCase()
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().onFailure { e ->
                Timber.e(e)
                _snackbar.emit("Sign out was failed")
            }
        }
    }
}
