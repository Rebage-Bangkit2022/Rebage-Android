package trashissue.rebage.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import trashissue.rebage.data.local.entity.UserEntity

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveUser(user: UserEntity) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.id
            preferences[KEY_USER_EMAIL] = user.email
            preferences[KEY_USER_NAME] = user.name
            if (user.photo != null) preferences[KEY_USER_PHOTO] = user.photo
            preferences[KEY_USER_TOKEN] = user.token
        }
    }

    fun getUser(): Flow<UserEntity?> {
        return dataStore.data.map { preferences ->
            UserEntity(
                id = preferences[KEY_USER_ID] ?: return@map null,
                name = preferences[KEY_USER_NAME] ?: return@map null,
                email = preferences[KEY_USER_NAME] ?: return@map null,
                photo = preferences[KEY_USER_PHOTO],
                token = preferences[KEY_USER_NAME] ?: return@map null
            )
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences.remove(KEY_USER_ID)
            mutablePreferences.remove(KEY_USER_NAME)
            mutablePreferences.remove(KEY_USER_NAME)
            mutablePreferences.remove(KEY_USER_PHOTO)
            mutablePreferences.remove(KEY_USER_TOKEN)
        }
    }

    suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        dataStore.edit { it[KEY_ONBOARDING] = isAlreadyOnboarding }
    }

    fun onboarding(): Flow<Boolean> {
        return dataStore.data.map { it[KEY_ONBOARDING] ?: false }
    }

    companion object {
        private val KEY_USER_ID = intPreferencesKey("user_id")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_PHOTO = stringPreferencesKey("user_photo")
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
        private val KEY_ONBOARDING = booleanPreferencesKey("onboarding")
    }
}
