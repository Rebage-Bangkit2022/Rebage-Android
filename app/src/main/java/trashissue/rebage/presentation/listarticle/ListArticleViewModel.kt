package trashissue.rebage.presentation.listarticle

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
import trashissue.rebage.domain.usecase.GetArticlesUseCase
import trashissue.rebage.domain.usecase.GetFavoriteArticlesUseCase
import trashissue.rebage.presentation.main.Route
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher,
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {

    private val _articles = MutableStateFlow(emptyList<Article>())
    val articles = _articles.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _type = MutableStateFlow(ListArticleType.Favorite)
    val type = _type.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun loadArticles() {
        savedStateHandle.get<String>(Route.LIST_ARTICLE_TYPE)?.let { type ->
            _type.value = ListArticleType.valueOf(type)

            Timber.i("WADUH $type")

            when (ListArticleType.valueOf(type)) {
                ListArticleType.Favorite -> loadFavoriteArticles()
                ListArticleType.Latest -> loadLatestArticles()
                ListArticleType.Reduce -> loadArticlesReduce()
                ListArticleType.Reuse -> loadArticlesReuse()
            }
        }
    }

    private fun loadFavoriteArticles() {
        viewModelScope.launch(dispatcher) {
            getFavoriteArticlesUseCase()
                .onSuccess { articles ->
                    _articles.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load favorite articles")
                }
        }
    }

    private fun loadLatestArticles() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase()
                .onSuccess { articles ->
                    _articles.value = articles
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
                    _articles.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about handicraft")
                }
        }
    }

    private fun loadArticlesReuse() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase(category = "Reuse")
                .onSuccess { articles ->
                    _articles.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reuse")
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("DIHAPUS NIH")
    }
}
