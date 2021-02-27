package com.example.mvvm.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


object DataStoreProvider {


    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data.catch { emit(emptyPreferences()) }
            .map { preference -> preference[key] }
    }

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

}