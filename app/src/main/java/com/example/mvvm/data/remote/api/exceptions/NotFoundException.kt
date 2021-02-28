package com.example.mvvm.data.remote.api.exceptions

import java.io.IOException

class NotFoundException : IOException() {

    override val message: String?
        get() = "Not Found"
}