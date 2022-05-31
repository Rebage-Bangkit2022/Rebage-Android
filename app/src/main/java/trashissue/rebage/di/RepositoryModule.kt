package trashissue.rebage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import trashissue.rebage.data.DefaultGarbageRepository
import trashissue.rebage.data.DefaultUserRepository
import trashissue.rebage.data.local.UserLocalDataSource
import trashissue.rebage.data.remote.GarbageRemoteDataSource
import trashissue.rebage.data.remote.UserRemoteDataSource
import trashissue.rebage.domain.repository.GarbageRepository
import trashissue.rebage.domain.repository.UserRepository
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
    fun provideGarbageRepository(garbageRemoteDataSource: GarbageRemoteDataSource): GarbageRepository {
        return DefaultGarbageRepository(garbageRemoteDataSource)
    }
}
