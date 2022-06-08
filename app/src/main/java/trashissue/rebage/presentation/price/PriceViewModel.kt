package trashissue.rebage.presentation.price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor() : ViewModel() {
    val state= MutableStateFlow(0)

    init {
        viewModelScope.launch {
            repeat(1000) {
                delay(1000)
                state.value = it
            }
        }
    }
}
