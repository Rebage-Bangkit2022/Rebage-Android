package trashissue.rebage.presentation.maps

import androidx.lifecycle.SavedStateHandle
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
import trashissue.rebage.domain.model.Place
import trashissue.rebage.domain.usecase.GetPlaceUseCase
import trashissue.rebage.presentation.main.Route
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getPlaceUseCase: GetPlaceUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _place = MutableStateFlow<Place?>(null)
    val place = _place.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadPlace()
    }

    private fun loadPlace() {
        viewModelScope.launch(dispatcher) {
            val placeId = savedStateHandle.get<String>(Route.KEY_PLACE_ID) ?: return@launch
            getPlaceUseCase(placeId)
                .onSuccess { place ->
                    _place.value = place
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load place")
                }
        }
    }
}
