package trashissue.rebage.presentation.detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import trashissue.rebage.domain.usecase.DetectGarbageUseCase
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetectionViewModel @Inject constructor(
    private val detectGarbageUseCase: DetectGarbageUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val detectGarbageResult = detectGarbageUseCase.result

    fun detectGarbage(file: File) {
        viewModelScope.launch(dispatcher) {
            detectGarbageUseCase(file)
        }
    }
}
