package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.Place
import trashissue.rebage.domain.repository.GoogleMapsRepository

class GetGarbageBankUseCase(
    private val googleMapsRepository: GoogleMapsRepository
) {

    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        radius: Double = 5 * 1500.0
    ): Result<List<Place>> = runCatching {
        googleMapsRepository.getNearby(lat, lng, radius, "Bank Sampah", "Bank Sampah")
    }
}
