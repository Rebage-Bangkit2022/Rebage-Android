package trashissue.rebage.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import trashissue.rebage.data.remote.payload.*

interface UserService {

    @POST("api/user/signup")
    suspend fun signUp(
        @Body
        req: SignUpRequest
    ): Response<Api<UserResponse>>

    @POST("api/user/signin")
    suspend fun signIn(
        @Body
        req: SignInRequest
    ): Response<Api<UserResponse>>

    @POST("api/user/auth-google")
    suspend fun authGoogle(
        @Body
        req: AuthGoogleRequest
    ): Response<Api<UserResponse>>

    @PUT("/api/user/edit")
    suspend fun edit(
        @Header("Authorization")
        token: String,
        @Body
        req: EditUserRequest
    ): Response<Api<UserResponse>>
}
