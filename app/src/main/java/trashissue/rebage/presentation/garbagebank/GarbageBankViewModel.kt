package trashissue.rebage.presentation.garbagebank

import androidx.core.location.component1
import androidx.core.location.component2
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import trashissue.rebage.domain.usecase.GetGarbageBankUseCase
import trashissue.rebage.presentation.common.locationFlow
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class GarbageBankViewModel @Inject constructor(
    fusedLocationProviderClient: FusedLocationProviderClient,
    dispatcher: CoroutineDispatcher,
    private val getGarbageBankUseCase: GetGarbageBankUseCase,
) : ViewModel() {
    private val locationFlow = fusedLocationProviderClient
        .locationFlow()
        .shareIn(viewModelScope, SharingStarted.Lazily)

    private val permissionGranted = MutableStateFlow(false)
    val places = permissionGranted
        .flatMapLatest {
            if (it) locationFlow else throw RuntimeException("Permission is needed")
        }
        .map { (lat, lng) ->
            _loading.value = true
            val places = getGarbageBankUseCase(lat, lng).getOrThrow()
            _loading.value = false
            places
        }
        .catch { e ->
            Timber.e(e)
        }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun loadPlacesIfPermissionGranted(granted: Boolean) {
        permissionGranted.value = granted
    }
}
