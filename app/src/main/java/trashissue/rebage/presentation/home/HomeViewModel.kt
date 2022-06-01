package trashissue.rebage.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.usecase.GetArticlesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getArticlesUseCase: GetArticlesUseCase,
    dispatcher: CoroutineDispatcher
) : ViewModel() {
    val allArticles = getArticlesUseCase()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Empty)
    val handicrafts = getArticlesUseCase(category = "reuse")
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Empty)
    val reduce = getArticlesUseCase(category = "reduce")
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Empty)
}
