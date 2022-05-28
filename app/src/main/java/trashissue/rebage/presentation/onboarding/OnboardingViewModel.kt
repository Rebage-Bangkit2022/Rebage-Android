package trashissue.rebage.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.OnboardingUseCase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase
) : ViewModel() {

    fun onboarding(isAlreadyOnboarding: Boolean) {
        viewModelScope.launch { onboardingUseCase(isAlreadyOnboarding) }
    }
}
