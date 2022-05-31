package trashissue.rebage.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import trashissue.rebage.domain.usecase.GetArticleUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getArticleUseCase: GetArticleUseCase
) : ViewModel() {
    val allArticles = getArticleUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val handicrafts = getArticleUseCase(category = "reuse")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val reduce = getArticleUseCase(category = "reduce")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
