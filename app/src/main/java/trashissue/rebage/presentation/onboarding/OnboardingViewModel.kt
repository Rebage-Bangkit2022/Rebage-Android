package trashissue.rebage.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.OnboardingUseCase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun onboarding(isAlreadyOnboarding: Boolean) {
        viewModelScope.launch(dispatcher) {
            onboardingUseCase(isAlreadyOnboarding)
        }
    }
}
