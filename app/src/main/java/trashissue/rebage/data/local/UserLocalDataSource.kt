package trashissue.rebage.data.local

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.data.local.datastore.UserPreferences
import trashissue.rebage.data.local.entity.UserEntity

class UserLocalDataSource(
    private val userPreferences: UserPreferences
) {

    suspend fun saveUser(user: UserEntity) {
        userPreferences.saveUser(user)
    }

    fun getUser(): Flow<UserEntity?> {
        return userPreferences.getUser()
    }

    suspend fun deleteUser() {
        userPreferences.deleteUser()
    }

    suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        userPreferences.onboarding(isAlreadyOnboarding)
    }

    fun onboarding(): Flow<Boolean> {
        return userPreferences.onboarding()
    }
}
