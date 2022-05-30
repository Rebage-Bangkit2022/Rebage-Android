package trashissue.rebage.domain.usecase

import kotlinx.coroutines.delay
import timber.log.Timber
import trashissue.rebage.domain.model.DetectedGarbage
import trashissue.rebage.domain.model.Result
import java.io.File

class DetectGarbageUseCase : FlowUseCase<Result<DetectedGarbage>>(Result.Empty) {

    suspend operator fun invoke(file: File) {
        Timber.i("HASIL SEBELUM delay emit")
        emit(Result.Loading)
        delay(6000)
        Timber.i("HASIL SESUDAH delay emit")
        emit(
            Result.Success(
                DetectedGarbage(
                    image = "https://res.cloudinary.com/dzj27omy0/image/upload/v1653876168/sampah/20220514_181114_eggufo.jpg",
                    detected = listOf(
                        DetectedGarbage.Detected(
                            boundingBox = listOf(
                                0.197199911F,
                                0.295953751F,
                                0.706323862F,
                                0.646190047F
                            ),
                            label = "botolplastik",
                            score = 0.995764554F
                        )
                    )
                )
            )
        )
        Timber.i("HASIL udah emit")
    }
}
