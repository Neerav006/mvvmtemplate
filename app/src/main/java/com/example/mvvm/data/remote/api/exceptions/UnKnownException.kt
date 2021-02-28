package com.example.mvvm.data.remote.api.exceptions

import java.lang.Exception

class UnKnownException : Exception() {

    override val message: String?
        get() = "Some Unknown Error Occurred"
}