package trashissue.rebage.presentation.common.component

import androidx.compose.runtime.Composable
import trashissue.rebage.domain.model.Result
import trashissue.rebage.domain.model.onSuccess

@Composable
inline fun <T> OnSuccess(result: Result<T>, content: @Composable (T) -> Unit) {
    result.onSuccess { content(it) }
}
