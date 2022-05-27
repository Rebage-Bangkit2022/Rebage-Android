package trashissue.rebage.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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

    suspend fun deleteUser() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences.remove(KEY_USER_ID)
            mutablePreferences.remove(KEY_USER_NAME)
            mutablePreferences.remove(KEY_USER_EMAIL)
            mutablePreferences.remove(KEY_USER_PHOTO)
            mutablePreferences.remove(KEY_USER_TOKEN)
        }
    }

    companion object {
        private val KEY_USER_ID = intPreferencesKey("user_id")
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_PHOTO = stringPreferencesKey("user_photo")
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
    }
}
