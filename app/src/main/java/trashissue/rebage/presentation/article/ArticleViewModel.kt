package trashissue.rebage.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.GetArticleUseCase
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {
    val articleResult = getArticleUseCase.result

    fun fetchArticle(articleId: Int) {
        viewModelScope.launch { getArticleUseCase(articleId) }
    }
}
