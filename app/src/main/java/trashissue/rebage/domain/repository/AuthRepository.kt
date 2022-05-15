package trashissue.rebage.domain.repository

import trashissue.rebage.domain.model.User

interface AuthRepository {

    suspend fun signUp(name: String, email: String, password: String): User

    suspend fun signIn(email: String, password: String): User

    suspend fun authGoogle(googleToken: String): User

    suspend fun signOut()
}