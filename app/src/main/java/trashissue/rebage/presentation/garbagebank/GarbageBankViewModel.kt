package trashissue.rebage.presentation.garbagebank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.usecase.GetGarbageBankUseCase
import javax.inject.Inject

@HiltViewModel
class GarbageBankViewModel @Inject constructor(
    private val getGarbageBankUseCase: GetGarbageBankUseCase,
    dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val location = MutableSharedFlow<Pair<Double, Double>>()

    val places = location
        .distinctUntilChanged()
        .map { location ->
            val (lat, lng) = location
            _loading.value = true
            val places = getGarbageBankUseCase(lat, lng)
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load garbage bank")
                }
                .getOrNull()
            _loading.value = false
            places ?: emptyList()
        }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun loadPlaces(lat: Double, lng: Double) {
        viewModelScope.launch {
            location.emit(lat to lng)
        }
    }
}
