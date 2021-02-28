package com.example.mvvm.data.remote.api.exceptions

import java.io.IOException

class NoInternetException : IOException() {

    override val message: String?
        get() = "No Internet Connection"
}