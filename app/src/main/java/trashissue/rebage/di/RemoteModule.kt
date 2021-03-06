package trashissue.rebage.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import trashissue.rebage.BuildConfig
import trashissue.rebage.data.remote.*
import trashissue.rebage.data.remote.service.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }

        val callFactory = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource {
        val userService = retrofit.create<UserService>()
        return UserRemoteDataSource(userService)
    }

    @Provides
    @Singleton
    fun provideDetectionRemoteDataSource(retrofit: Retrofit): DetectionRemoteDataSource {
        val detectionService = retrofit.create<DetectionService>()
        return DetectionRemoteDataSource(detectionService)
    }

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(retrofit: Retrofit): ArticleRemoteDataSource {
        val articleService = retrofit.create<ArticleService>()
        return ArticleRemoteDataSource(articleService)
    }

    @Provides
    @Singleton
    fun provideGarbageRemoteDataSource(retrofit: Retrofit): GarbageRemoteDataSource {
        val garbageService = retrofit.create<GarbageService>()
        return GarbageRemoteDataSource(garbageService)
    }

    @Provides
    @Singleton
    fun provideGoogleMapRemoteDataSource(
        @ApplicationContext context: Context,
        retrofit: Retrofit
    ): GoogleMapRemoteDataSource {
        val googleMapService = retrofit.create<GoogleMapService>()

        Places.initialize(context, BuildConfig.GOOGLE_MAPS_WEB_KEY)
        val placesClient = Places.createClient(context)

        return GoogleMapRemoteDataSource(googleMapService, placesClient)
    }
}
