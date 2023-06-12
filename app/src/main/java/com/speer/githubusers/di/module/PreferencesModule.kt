package com.speer.githubusers.di.module

import com.speer.githubusers.data.prefs.PreferencesHelper
import com.speer.githubusers.data.prefs.PreferencesHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Singleton
    @Provides
    fun providePreferencesHelper(preferencesHelperImpl: PreferencesHelperImpl): PreferencesHelper =
        preferencesHelperImpl
}
