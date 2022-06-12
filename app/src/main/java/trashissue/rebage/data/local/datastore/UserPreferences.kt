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
            preferences[KeyUserId] = user.id
            preferences[KeyUserEmail] = user.email
            preferences[KeyUsername] = user.name
            if (user.photo != null) preferences[KeyUserPhoto] = user.photo
            preferences[KeyUserToken] = user.token
        }
    }

    fun getUser(): Flow<UserEntity?> {
        return dataStore.data.map { preferences ->
            UserEntity(
                id = preferences[KeyUserId] ?: return@map null,
                name = preferences[KeyUsername] ?: return@map null,
                email = preferences[KeyUserEmail] ?: return@map null,
                photo = preferences[KeyUserPhoto],
                token = preferences[KeyUserToken] ?: return@map null
            )
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences.remove(KeyUserId)
            mutablePreferences.remove(KeyUsername)
            mutablePreferences.remove(KeyUsername)
            mutablePreferences.remove(KeyUserPhoto)
            mutablePreferences.remove(KeyUserToken)
        }
    }

    suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        dataStore.edit { it[KeyOnboarding] = isAlreadyOnboarding }
    }

    fun onboarding(): Flow<Boolean> {
        return dataStore.data.map { it[KeyOnboarding] ?: false }
    }

    suspend fun darkTheme(isDarkTheme: Boolean?) {
        dataStore.edit { mutablePreferences ->
            if (isDarkTheme != null) {
                mutablePreferences[KeyDarkTheme] = isDarkTheme
                return@edit
            }
            mutablePreferences.remove(KeyDarkTheme)
        }
    }

    fun darkTheme(): Flow<Boolean?> {
        return dataStore.data.map { it[KeyDarkTheme] }
    }

    companion object {
        private val KeyUserId = intPreferencesKey("user_id")
        private val KeyUsername = stringPreferencesKey("user_name")
        private val KeyUserEmail = stringPreferencesKey("user_email")
        private val KeyUserPhoto = stringPreferencesKey("user_photo")
        private val KeyUserToken = stringPreferencesKey("user_token")
        private val KeyOnboarding = booleanPreferencesKey("onboarding")
        private val KeyDarkTheme = booleanPreferencesKey("dark_theme")
    }
}
