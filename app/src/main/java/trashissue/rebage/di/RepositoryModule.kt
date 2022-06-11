package trashissue.rebage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import trashissue.rebage.data.*
import trashissue.rebage.data.local.DetectionLocalDataSource
import trashissue.rebage.data.local.UserLocalDataSource
import trashissue.rebage.data.remote.*
import trashissue.rebage.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return DefaultUserRepository(
            userLocalDataSource = userLocalDataSource,
            userRemoteDataSource = userRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideDetectionRepository(
        detectionLocalDataSource: DetectionLocalDataSource,
        detectionRemoteDataSource: DetectionRemoteDataSource
    ): DetectionRepository {
        return DefaultDetectionRepository(
            detectionLocalDataSource,
            detectionRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository {
        return DefaultArticleRepository(articleRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGarbageRepository(garbageRemoteDataSource: GarbageRemoteDataSource): GarbageRepository {
        return DefaultGarbageRepository(garbageRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGoogleMapRepository(googleMapRemoteDataSource: GoogleMapRemoteDataSource): GoogleMapsRepository {
        return DefaultGoogleMapsRepository(googleMapRemoteDataSource)
    }
}
