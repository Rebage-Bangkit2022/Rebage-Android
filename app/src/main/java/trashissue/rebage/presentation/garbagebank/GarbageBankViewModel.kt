package trashissue.rebage.presentation.garbagebank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.model.GarbageBank
import trashissue.rebage.domain.usecase.GetGarbageBankUseCase
import javax.inject.Inject

@HiltViewModel
class GarbageBankViewModel @Inject constructor(
    private val getGarbageBankUseCase: GetGarbageBankUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _garbageBanks = MutableStateFlow(emptyList<GarbageBank>())
    val garbageBanks = _garbageBanks.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun loadGarbageBank(lat: Double, lng: Double) {
        viewModelScope.launch(dispatcher) {

            getGarbageBankUseCase(lat, lng)
                .onSuccess { garbageBank ->
                    _garbageBanks.value = garbageBank
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load garbage bank")
                }
        }
    }
}
