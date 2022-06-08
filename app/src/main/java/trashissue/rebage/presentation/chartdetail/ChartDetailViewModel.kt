package trashissue.rebage.presentation.chartdetail

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
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.domain.usecase.GetDetectionsStatisticUseCase
import javax.inject.Inject

@HiltViewModel
class ChartDetailViewModel @Inject constructor(
    private val getDetectionsStatisticUseCase: GetDetectionsStatisticUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _stats = MutableStateFlow(emptyList<DetectionStatistic>())
    val stats = _stats.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadDetectionsStatistic()
    }

    private fun loadDetectionsStatistic() {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            getDetectionsStatisticUseCase()
                .onSuccess { stats ->
                    _stats.value = stats
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reduce")
                }
            _loading.value = false
        }
    }
}
