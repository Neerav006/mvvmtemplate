package com.example.mvvm.data.session

import kotlinx.coroutines.flow.Flow

/**
 * Created by Niharika
 **/
interface UserPreferenceManager {
    suspend fun setUserName(name: String)

    suspend fun getUserUserName(): Flow<String?>
}