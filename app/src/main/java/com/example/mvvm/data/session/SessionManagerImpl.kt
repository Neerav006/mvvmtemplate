package com.example.mvvm.data.session
import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(context: Context) : SessionManager {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(token: String?) {
        sharedPreferences.edit {
            putString(KEY_TOKEN, token)
            commit()
        }
    }

    override fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

    companion object {
        const val FILE_NAME = "auth_shared_pref"
        const val KEY_TOKEN = "auth_token"
    }
}