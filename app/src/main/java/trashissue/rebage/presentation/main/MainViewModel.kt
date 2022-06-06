package trashissue.rebage.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import trashissue.rebage.domain.usecase.GetUserUseCase
import trashissue.rebage.domain.usecase.OnboardingUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    onboardingUseCase: OnboardingUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    val isAlreadyOnboarding = onboardingUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    val isLoggedIn = getUserUseCase()
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
}
