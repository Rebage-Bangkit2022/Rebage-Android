package trashissue.rebage.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import trashissue.rebage.data.local.UserLocalDataSource
import trashissue.rebage.data.mapper.asEntity
import trashissue.rebage.data.mapper.asModel
import trashissue.rebage.data.remote.UserRemoteDataSource
import trashissue.rebage.data.remote.payload.AuthGoogleRequest
import trashissue.rebage.data.remote.payload.EditUserRequest
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
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun signIn(email: String, password: String): User {
        val req = SignInRequest(
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signIn(req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun authGoogle(idToken: String): User {
        val req = AuthGoogleRequest(idToken = idToken)
        val res = userRemoteDataSource.authGoogle(req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun signOut() {
        userLocalDataSource.delete()
    }

    override fun getUser(): Flow<User?> {
        return userLocalDataSource.getUser().map { it?.asModel() }
    }

    override suspend fun edit(token: String, name: String, password: String, photo: String?): User {
        val req = EditUserRequest(
            name = name,
            password = password,
            photo = photo
        )
        val res = userRemoteDataSource.edit(token, req)
        userLocalDataSource.save(res.asEntity())
        return res.asModel()
    }

    override suspend fun onboarding(isAlreadyOnboarding: Boolean) {
        userLocalDataSource.onboarding(isAlreadyOnboarding)
    }

    override fun onboarding(): Flow<Boolean> {
        return userLocalDataSource.onboarding()
    }

    override suspend fun darkTheme(isDarkTheme: Boolean?) {
        userLocalDataSource.darkTheme(isDarkTheme)
    }

    override fun darkTheme(): Flow<Boolean?> {
        return userLocalDataSource.darkTheme()
    }
}
