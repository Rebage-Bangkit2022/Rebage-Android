package trashissue.rebage.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.model.DetectionStatistic
import trashissue.rebage.domain.usecase.GetArticlesUseCase
import trashissue.rebage.domain.usecase.GetDetectionsStatisticUseCase
import trashissue.rebage.domain.usecase.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getDetectionsStatisticUseCase: GetDetectionsStatisticUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val username = getUserUseCase()
        .map { it?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _stats = MutableStateFlow(emptyList<DetectionStatistic>())
    val stats = _stats.asStateFlow()

    private val _allArticles = MutableStateFlow(emptyList<Article>())
    val allArticles = _allArticles.asStateFlow()

    private val _articlesReduce = MutableStateFlow(emptyList<Article>())
    val articlesReduce = _articlesReduce.asStateFlow()

    private val _articleReuse = MutableStateFlow(emptyList<Article>())
    val articleReuse = _articleReuse.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadDetectionsStatistic()
        loadAllArticles()
        loadArticlesReduce()
        loadArticlesReuse()
    }

    private fun loadDetectionsStatistic() {
        viewModelScope.launch(dispatcher) {
            getDetectionsStatisticUseCase()
                .onSuccess { stats ->
                    _stats.value = stats
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reduce")
                }
        }
    }

    private fun loadAllArticles() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase()
                .onSuccess { articles ->
                    _allArticles.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles")
                }
        }
    }

    private fun loadArticlesReduce() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase(category = "Reduce")
                .onSuccess { articles ->
                    _articlesReduce.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reduce")
                }
        }
    }

    private fun loadArticlesReuse() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase(category = "Reuse")
                .onSuccess { articles ->
                    _articleReuse.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reuse")
                }
        }
    }
}
