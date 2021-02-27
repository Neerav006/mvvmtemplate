package com.example.mvvm.di.module

import android.app.Application
import android.content.SharedPreferences
import com.example.mvvm.data.session.SessionManager
import com.example.mvvm.data.session.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    fun provideUserPreferenceManager(application: Application): SessionManager {
        return SessionManagerImpl(application)
    }

}