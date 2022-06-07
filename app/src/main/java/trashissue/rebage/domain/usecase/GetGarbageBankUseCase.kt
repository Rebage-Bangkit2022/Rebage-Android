package trashissue.rebage.domain.usecase

import trashissue.rebage.domain.model.GarbageBank
import trashissue.rebage.domain.repository.GoogleMapRepository

class GetGarbageBankUseCase(
    private val googleMapRepository: GoogleMapRepository
) {

    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        radius: Double = 1500.0
    ): Result<List<GarbageBank>> = runCatching {
        googleMapRepository.getNearby(lat, lng, radius, "Bank Sampah", "Bank Sampah")
    }
}
