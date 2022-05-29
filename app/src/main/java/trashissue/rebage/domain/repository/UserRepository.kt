package trashissue.rebage.domain.repository

import kotlinx.coroutines.flow.Flow
import trashissue.rebage.domain.model.User

interface UserRepository {

    suspend fun signUp(name: String, email: String, password: String): User

    suspend fun signIn(email: String, password: String): User

    suspend fun authGoogle(idToken: String): User

    suspend fun signOut()

    fun getUser(): Flow<User?>

    suspend fun onboarding(isAlreadyOnboarding: Boolean)

    fun onboarding(): Flow<Boolean>
}
