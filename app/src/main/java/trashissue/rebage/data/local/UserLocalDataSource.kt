package trashissue.rebage.data.local

import trashissue.rebage.data.local.datastore.UserPreferences
import trashissue.rebage.data.local.entity.UserEntity

class UserLocalDataSource(
    private val userPreferences: UserPreferences
) {

    suspend fun saveUser(user: UserEntity) {
        userPreferences.saveUser(user)
    }

    suspend fun deleteUser() {
        userPreferences.deleteUser()
    }
}
