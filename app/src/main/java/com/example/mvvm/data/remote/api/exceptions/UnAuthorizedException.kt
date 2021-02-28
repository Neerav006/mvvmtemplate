package com.example.mvvm.data.remote.api.exceptions

import java.io.IOException

class UnAuthorizedException : IOException() {

    override val message: String?
        get() = "User Unauthorized"
}