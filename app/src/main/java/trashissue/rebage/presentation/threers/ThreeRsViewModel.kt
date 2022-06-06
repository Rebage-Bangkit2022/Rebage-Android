package trashissue.rebage.presentation.threers

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
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.Detection
import trashissue.rebage.domain.model.Garbage
import trashissue.rebage.domain.usecase.GetArticlesUseCase
import trashissue.rebage.domain.usecase.GetDetectionUseCase
import trashissue.rebage.domain.usecase.GetGarbageUseCase
import trashissue.rebage.domain.usecase.UpdateDetectionUseCase
import trashissue.rebage.presentation.main.Route
import javax.inject.Inject

@HiltViewModel
class ThreeRsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getDetectionUseCase: GetDetectionUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getGarbageUseCase: GetGarbageUseCase,
    private val updateDetectionUseCase: UpdateDetectionUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _detection = MutableStateFlow<Detection?>(null)
    val detection = _detection.asStateFlow()

    private val _articlesReduce = MutableStateFlow(emptyList<Article>())
    val articlesReduce = _articlesReduce.asStateFlow()

    private val _articleReuse = MutableStateFlow(emptyList<Article>())
    val articleReuse = _articleReuse.asStateFlow()

    private val _garbage = MutableStateFlow<Garbage?>(null)
    val garbage = _garbage.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadDetection { detection ->
            // Concurrent load
            loadGarbage(detection.label)
            loadArticlesReduce(detection.label)
            loadArticlesReuse(detection.label)
        }
    }

    private fun loadGarbage(name: String) {
        viewModelScope.launch {
            getGarbageUseCase(name)
                .onSuccess { garbage ->
                    _garbage.value = garbage
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load garbage")
                }
        }
    }

    private fun loadDetection(onSuccess: (Detection) -> Unit) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            val id = savedStateHandle.get<Int>(Route.KEY_DETECTION_ID) ?: return@launch
            getDetectionUseCase(id)
                .onSuccess { detection ->
                    _detection.value = detection
                    onSuccess(detection)
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load detection")
                }
            _loading.value = false
        }
    }

    private fun loadArticlesReduce(garbageCategory: String) {
        viewModelScope.launch {
            getArticlesUseCase(category = "Reduce", garbageCategory = garbageCategory)
                .onSuccess { articles ->
                    _articlesReduce.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reduce")
                }
        }
    }

    private fun loadArticlesReuse(garbageCategory: String) {
        viewModelScope.launch {
            getArticlesUseCase(category = "Reuse", garbageCategory = garbageCategory)
                .onSuccess { articles ->
                    _articleReuse.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reuse")
                }
        }
    }

    fun update(id: Int, total: Int) {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            updateDetectionUseCase(id, total)
                .onSuccess { detection ->
                    _detection.value = detection
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reuse")
                }
            _loading.value = false
        }
    }
}
