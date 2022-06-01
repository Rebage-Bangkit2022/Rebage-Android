package trashissue.rebage.presentation.threers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.usecase.GetArticlesUseCase
import javax.inject.Inject

@HiltViewModel
class ThreeRsViewModel @Inject constructor(
    getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    val reuse = getArticlesUseCase("reuse")
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Empty)
    val reduce = getArticlesUseCase("reduce")
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Empty)
}
