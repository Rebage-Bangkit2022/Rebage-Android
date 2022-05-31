package trashissue.rebage.presentation.detection.component

import androidx.compose.runtime.*
import trashissue.rebage.domain.model.DetectedGarbage
import trashissue.rebage.domain.model.Result

class BoundingBoxScaffoldState() {
    var isLoading by mutableStateOf(false)
    var showPreview by mutableStateOf<Result<DetectedGarbage>>(Result.Empty)
}

@Composable
fun rememberDetectionScaffoldState(): BoundingBoxScaffoldState {
    return remember { BoundingBoxScaffoldState() }
}
