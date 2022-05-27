package trashissue.rebage.data.remote

import trashissue.rebage.data.remote.payload.SignInRequest
import trashissue.rebage.data.remote.payload.SignUpRequest
import trashissue.rebage.data.remote.payload.TokenResponse
import trashissue.rebage.data.remote.service.UserService

class UserRemoteDataSource(
    private val userService: UserService
) {

    suspend fun signUp(req: SignUpRequest): TokenResponse {
        val res = userService.signUp(req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun signIn(req: SignInRequest): TokenResponse {
        val res = userService.signIn(req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data

        return data ?: throw RuntimeException("Response body is empty")
    }
}
