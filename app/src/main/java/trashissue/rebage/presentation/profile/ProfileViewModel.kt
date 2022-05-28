package trashissue.rebage.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.SignOutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch { signOutUseCase() }
    }
}
