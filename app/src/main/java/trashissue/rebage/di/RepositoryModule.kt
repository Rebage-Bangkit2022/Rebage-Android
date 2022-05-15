package trashissue.rebage.di

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import trashissue.rebage.domain.repository.AuthRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Singleton
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = TODO()
}