package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Place
import trashissue.rebage.domain.repository.GoogleMapsRepository

class GetPlaceUseCase(
    private val googleMapsRepository: GoogleMapsRepository
) {

    suspend operator fun invoke(placeId: String): Result<Place> = runCatching {
        googleMapsRepository.getPlace(placeId)
    }
}
