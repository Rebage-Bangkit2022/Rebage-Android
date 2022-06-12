package trashissue.rebage.presentation.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import trashissue.rebage.domain.model.Article
import trashissue.rebage.domain.usecase.GetArticleUseCase
import trashissue.rebage.domain.usecase.ToggleLikeArticleUseCase
import trashissue.rebage.presentation.main.Route
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val toggleLikeArticleUseCase: ToggleLikeArticleUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private var job: Job? = null

    init {
        loadArticle()
    }

    fun toggleLike(articleId: Int) {
        job?.cancel()
        _article.value?.let { prevValue ->
            job = viewModelScope.launch(dispatcher) {
                _article.value = _article.value?.copy(liked = !prevValue.liked)
                delay(250)
               toggleLikeArticleUseCase.invoke(articleId, !prevValue.liked)
                   .onSuccess { article->
                       _article.value = article
                   }
                   .onFailure {
                       _article.value = prevValue
                   }
            }
        }
    }

    private fun loadArticle() {
        viewModelScope.launch(dispatcher) {
            val id = savedStateHandle.get<Int>(Route.KEY_ARTICLE_ID) ?: return@launch
            getArticleUseCase(id)
                .onSuccess { article ->
                    _article.value = article
                }
                .onFailure { e ->
                    _snackbar.emit(e.message ?: "Failed to load article")
                }
        }
    }
}
