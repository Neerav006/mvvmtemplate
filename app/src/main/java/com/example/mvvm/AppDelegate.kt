package com.example.mvvm

import android.app.Application
import com.example.mvvm.data.session.DataStoreProvider
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppDelegate :Application() {

    override fun onCreate() {
        super.onCreate()
        DataStoreProvider.init(this)

    }


}