package com.example.mvvm.data.remote.api

import android.util.Log
import com.example.mvvm.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder().apply {
            sessionManager.getToken()?.let {
                Log.e("header",sessionManager.getToken()?:"")
                header("Authorization", "Bearer $it")
                addHeader("custom","dummy")
            }
        }.build()
        return chain.proceed(authRequest)
    }
}