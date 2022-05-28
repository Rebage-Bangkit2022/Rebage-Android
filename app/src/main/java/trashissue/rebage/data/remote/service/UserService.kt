package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import trashissue.rebage.data.remote.payload.Api
import trashissue.rebage.data.remote.payload.SignInRequest
import trashissue.rebage.data.remote.payload.SignUpRequest
import trashissue.rebage.data.remote.payload.TokenResponse

interface UserService {

    @POST
    suspend fun signUp(@Body req: SignUpRequest): Response<Api<TokenResponse>>

    @POST
    suspend fun signIn(@Body req: SignInRequest): Response<Api<TokenResponse>>
}
