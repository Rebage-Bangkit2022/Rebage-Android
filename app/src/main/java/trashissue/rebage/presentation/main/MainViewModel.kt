package trashissue.rebage.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import trashissue.rebage.domain.usecase.DarkThemeUseCase
import trashissue.rebage.domain.usecase.GetUserUseCase
import trashissue.rebage.domain.usecase.OnboardingUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcher: CoroutineDispatcher,
    onboardingUseCase: OnboardingUseCase,
    getUserUseCase: GetUserUseCase,
    darkThemeUseCase: DarkThemeUseCase
) : ViewModel() {
    val onboarding = onboardingUseCase.invoke()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    val loggedIn = getUserUseCase()
        .map { it != null }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    val darkTheme = darkThemeUseCase()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
