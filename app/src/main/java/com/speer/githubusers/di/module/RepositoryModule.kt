package com.speer.githubusers.di.module

import com.speer.githubusers.data.api.ApiService
import com.speer.githubusers.data.repository.SearchHistoryRepositoryImpl
import com.speer.githubusers.data.repository.UserRepositoryImpl
import com.speer.githubusers.domain.repository.SearchHistoryRepository
import com.speer.githubusers.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    @Singleton
    fun provideSearchHistoryRepository(
        searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository = searchHistoryRepositoryImpl
}
