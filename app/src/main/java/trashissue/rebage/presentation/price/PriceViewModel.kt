package trashissue.rebage.presentation.price

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
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.domain.usecase.GetGarbageUseCase
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    private val getGarbageUseCase: GetGarbageUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _garbage = MutableStateFlow<List<Garbage>>(emptyList())
    val garbage = _garbage.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = MutableStateFlow(false)

    init {
        loadGarbage()
    }

    private fun loadGarbage() {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            getGarbageUseCase()
                .onSuccess { garbage ->
                    _garbage.value = garbage
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to load garbage")
                }
            _loading.value = false
        }
    }
}
