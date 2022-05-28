package trashissue.rebage.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import trashissue.rebage.data.local.UserLocalDataSource
import trashissue.rebage.data.mapper.asEntity
import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.UserRemoteDataSource
import trashissue.rebage.data.remote.payload.SignInRequest
import trashissue.rebage.data.remote.payload.SignUpRequest
import trashissue.rebage.domain.model.User
import trashissue.rebage.domain.repository.UserRepository

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun signUp(name: String, email: String, password: String): User {
        val req = SignUpRequest(
            name = name,
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signUp(req)
        userLocalDataSource.saveUser(res.asEntity())
        return res.asModel()
    }

    override suspend fun signIn(email: String, password: String): User {
        val req = SignInRequest(
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signIn(req)
        userLocalDataSource.saveUser(res.asEntity())
        return res.asModel()
    }

    override suspend fun authGoogle(googleToken: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        userLocalDataSource.deleteUser()
    }

    override fun getUser(): Flow<User?> {
        return userLocalDataSource.getUser()
            .flowOn(Dispatchers.IO)
            .map { it?.asModel() }
    }

    override suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        userLocalDataSource.onboarding(isAlreadyOnboarding)
    }

    override fun onboarding(): Flow<Boolean> {
        return userLocalDataSource.onboarding()
    }
}
