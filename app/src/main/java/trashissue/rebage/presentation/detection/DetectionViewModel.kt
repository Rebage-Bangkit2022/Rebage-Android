package trashissue.rebage.presentation.detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.domain.usecase.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetectionViewModel @Inject constructor(
    private val getGarbageUseCase: GetGarbageUseCase,
    private val detectGarbageUseCase: DetectGarbageUseCase,
    private val getDetectionUseCase: GetDetectionUseCase,
    private val updateDetectionUseCase: UpdateDetectionUseCase,
    private val deleteDetectionUseCase: DeleteDetectionUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _garbage = MutableStateFlow<List<Garbage>>(emptyList())
    val garbage = _garbage.asStateFlow()

    private val _detections = MutableStateFlow<List<Detection>>(emptyList())
    val detections = _detections.asStateFlow()

    private val _preview = MutableStateFlow<List<Detection>>(emptyList())
    val preview = _preview.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val garbageLoading = MutableStateFlow(false)
    private val detectionLoading = MutableStateFlow(false)
    val loading = combine(
        garbageLoading,
        detectionLoading
    ) { garbageLoading, detectionLoading ->
        garbageLoading || detectionLoading
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        loadGarbage()
        loadDetections()
    }

    private fun loadGarbage() {
        viewModelScope.launch(dispatcher) {
            garbageLoading.value = true
            getGarbageUseCase()
                .onSuccess { garbage ->
                    _garbage.value = garbage
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to load garbage")
                }
            garbageLoading.value = false
        }
    }

    fun detect(image: File) {
        viewModelScope.launch(dispatcher) {
            detectionLoading.value = true
            detectGarbageUseCase(image)
                .onSuccess { detections ->
                    _preview.value = detections
                    _detections.value = detections.plus(_detections.value)
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to detect object")
                }
            detectionLoading.value = false
        }
    }

    private fun loadDetections() {
        viewModelScope.launch(dispatcher) {
            detectionLoading.value = true
            getDetectionUseCase()
                .onSuccess { detections ->
                    _detections.value = detections
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to load detections")
                }
            detectionLoading.value = false
        }
    }

    fun update(id: Int, total: Int) {
        viewModelScope.launch(dispatcher) {
            detectionLoading.value = true
            updateDetectionUseCase(id, total)
                .onSuccess { detection ->
                    val index = _detections.value.indexOfFirst { it.id == id }
                    if (index == -1) {
                        _snackbar.emit("Failed to update detection")
                        return@onSuccess
                    }
                    _detections.value = _detections.value
                        .toMutableList()
                        .apply { this[index] = detection }
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to update detection")
                }
            detectionLoading.value = false
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(dispatcher) {
            detectionLoading.value = true
            deleteDetectionUseCase(id)
                .onSuccess { detection ->
                    val index = _detections.value.indexOfFirst { it.id == id }
                    if (index == -1) {
                        _snackbar.emit("Failed to delete detection")
                        return@onSuccess
                    }
                    _detections.value = _detections.value
                        .toMutableList()
                        .apply { remove(detection) }
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit(e.message ?: "Failed to delete detection")
                }
            detectionLoading.value = false
        }
    }

    fun showPreview(detections: List<Detection>) {
        _preview.value = detections
    }

    fun deletePreview() {
        _preview.value = emptyList()
    }
}
