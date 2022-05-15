package trashissue.rebage.data

import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.AuthRepository

class DefaultAuthRepository : AuthRepository {

    override suspend fun signUp(name: String, email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun authGoogle(googleToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}