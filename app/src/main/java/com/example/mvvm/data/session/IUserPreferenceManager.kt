package com.example.mvvm.data.session
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mvvm.data.session.DataStoreProvider.getValue
import com.example.mvvm.data.session.DataStoreProvider.setValue
import com.example.mvvm.data.session.IUserPreferenceManager.Keys.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class IUserPreferenceManager @Inject constructor() : UserPreferenceManager {

    override suspend fun setUserName(name: String) {
        setValue(USER_NAME, name)

    }

    override suspend fun getUserUserName(): Flow<String?> {
        return getValue(USER_NAME).map { it }
    }

    object Keys {
        val USER_NAME = stringPreferencesKey("user_name")
    }
}